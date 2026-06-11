import { useEffect, useState } from 'react'
import { getCharacters, createCharacter, searchCharacters } from '../api/characterApi'
import type { CharacterResponse } from '../api/types'
import { useEvents } from '../context/EventContext'
import { useUserMap } from '../hooks/useUserMap'

const TYPES = ['Assassin', 'Mage', 'Tank', 'Archer']
const TYPE_ICON: Record<string, string> = { Assassin: '🗡️', Mage: '🔮', Tank: '🛡️', Archer: '🏹' }

export default function Characters() {
  const { publish } = useEvents()
  const username = useUserMap()
  const [characters, setCharacters] = useState<CharacterResponse[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [search, setSearch] = useState('')
  const [showForm, setShowForm] = useState(false)
  const [form, setForm] = useState({ name: '', characterTypeName: 'Assassin', userId: '' })
  const [submitting, setSubmitting] = useState(false)

  async function load() {
    try {
      setCharacters(await getCharacters())
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : 'Error')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { load() }, [])

  async function handleSearch(e: React.FormEvent) {
    e.preventDefault()
    if (!search.trim()) { load(); return }
    setLoading(true)
    try {
      setCharacters(await searchCharacters(search))
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : 'Error')
    } finally {
      setLoading(false) }
  }

  async function handleCreate(e: React.FormEvent) {
    e.preventDefault()
    setSubmitting(true); setError('')
    try {
      const char = await createCharacter(form.name, form.characterTypeName, Number(form.userId))
      publish('CHARACTER_CREATED', 'character-events',
        `New character: ${char.name} (${char.characterType?.name}) for user #${form.userId}`, char)
      setShowForm(false)
      setForm({ name: '', characterTypeName: 'Assassin', userId: '' })
      load()
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : 'Error')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div>
      <div className="row-between mb-16">
        <h1 className="page-title" style={{ marginBottom: 0 }}>🎭 Characters</h1>
        <button className="btn btn-red" onClick={() => setShowForm(s => !s)}>
          {showForm ? '✕ Cancel' : '+ Create'}
        </button>
      </div>

      {showForm && (
        <form onSubmit={handleCreate} className="card card--red mb-24 anim-fade" style={{ maxWidth: 440 }}>
          <p className="section-title">New Character</p>
          <div className="col">
            <input className="input" placeholder="Name" required
              value={form.name} onChange={e => setForm(f => ({ ...f, name: e.target.value }))} />
            <select className="input" value={form.characterTypeName}
              onChange={e => setForm(f => ({ ...f, characterTypeName: e.target.value }))}>
              {TYPES.map(t => <option key={t} value={t}>{TYPE_ICON[t]} {t}</option>)}
            </select>
            <input className="input" type="number" placeholder="User ID" required
              value={form.userId} onChange={e => setForm(f => ({ ...f, userId: e.target.value }))} />
            <button className="btn btn-red w-full" type="submit" disabled={submitting}>
              {submitting ? 'Creating...' : 'Create Character'}
            </button>
          </div>
        </form>
      )}

      {/* Search */}
      <form onSubmit={handleSearch} className="row mb-24" style={{ maxWidth: 440 }}>
        <input className="input" placeholder="Search by name..." value={search}
          onChange={e => { setSearch(e.target.value); if (!e.target.value) load() }} />
        <button className="btn btn-outline" type="submit">Search</button>
      </form>

      {error && <div className="error-msg mb-16">{error}</div>}

      {loading ? (
        <div style={{ color: 'var(--text-dim)', fontFamily: 'var(--font-mono)' }}>Loading...</div>
      ) : (
        <div className="grid-3">
          {characters.map((c, i) => {
            const typeName = c.characterType?.name ?? ''
            const xpPct = Math.min(100, (c.experience % 100))
            return (
              <div key={c.id} className="card anim-fade" style={{ animationDelay: `${i * 0.04}s` }}>
                <div className="row-between mb-8">
                  <span style={{ fontSize: '1.6rem' }}>{TYPE_ICON[typeName] ?? '⚔️'}</span>
                  <span className={`badge badge-${typeName}`}>{typeName}</span>
                </div>
                <div style={{ fontFamily: 'var(--font-title)', fontSize: '1rem', marginBottom: 4 }}>{c.name}</div>
                <div style={{ fontSize: '.78rem', color: 'var(--text-dim)', fontFamily: 'var(--font-mono)', marginBottom: 12 }}>
                  {username(c.userId)} · ID #{c.id}
                </div>
                <div className="col gap-4">
                  <div className="stat-bar">
                    <div className="stat-bar-label"><span>HP</span><span>{c.characterType?.health}</span></div>
                    <div className="stat-bar-track"><div className="stat-bar-fill-hp" style={{ width: `${Math.min(100, (c.characterType?.health ?? 0) / 1.5)}%` }} /></div>
                  </div>
                  <div className="stat-bar">
                    <div className="stat-bar-label"><span>STR</span><span>{c.characterType?.strength}</span></div>
                    <div className="stat-bar-track"><div className="stat-bar-fill-str" style={{ width: `${Math.min(100, c.characterType?.strength ?? 0)}%` }} /></div>
                  </div>
                  <div className="stat-bar">
                    <div className="stat-bar-label"><span>XP</span><span>LVL {c.level}</span></div>
                    <div className="stat-bar-track">
                      <div style={{ height: '100%', borderRadius: 2, width: `${xpPct}%`, background: 'linear-gradient(90deg,#7c3aed,#c77dff)' }} />
                    </div>
                  </div>
                </div>
              </div>
            )
          })}
        </div>
      )}
    </div>
  )
}
