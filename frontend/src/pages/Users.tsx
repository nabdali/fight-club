import { useEffect, useState } from 'react'
import { getUsers, register } from '../api/userApi'
import type { UserDTO } from '../api/types'
import { useEvents } from '../context/EventContext'

export default function Users() {
  const { publish } = useEvents()
  const [users, setUsers] = useState<UserDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [showForm, setShowForm] = useState(false)
  const [form, setForm] = useState({ email: '', pseudo: '', password: '' })
  const [submitting, setSubmitting] = useState(false)

  async function load() {
    try {
      setUsers(await getUsers())
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : 'Error')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { load() }, [])

  async function handleRegister(e: React.FormEvent) {
    e.preventDefault()
    setSubmitting(true)
    try {
      const user = await register(form.email, form.pseudo, form.password)
      publish('USER_REGISTERED', 'user-events', `New user registered: ${user.pseudo} (${user.email})`, user)
      setShowForm(false)
      setForm({ email: '', pseudo: '', password: '' })
      load()
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : 'Error')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div>
      <div className="row-between mb-24">
        <h1 className="page-title" style={{ marginBottom: 0 }}>👤 Users</h1>
        <button className="btn btn-red" onClick={() => setShowForm(s => !s)}>
          {showForm ? '✕ Cancel' : '+ Register'}
        </button>
      </div>

      {showForm && (
        <form onSubmit={handleRegister} className="card card--red mb-24 anim-fade" style={{ maxWidth: 440 }}>
          <p className="section-title">New User</p>
          <div className="col">
            <input className="input" type="email" placeholder="Email" required
              value={form.email} onChange={e => setForm(f => ({ ...f, email: e.target.value }))} />
            <input className="input" type="text" placeholder="Pseudo" required
              value={form.pseudo} onChange={e => setForm(f => ({ ...f, pseudo: e.target.value }))} />
            <input className="input" type="password" placeholder="Password" required
              value={form.password} onChange={e => setForm(f => ({ ...f, password: e.target.value }))} />
            <button className="btn btn-red w-full" type="submit" disabled={submitting}>
              {submitting ? 'Registering...' : 'Create Account'}
            </button>
          </div>
        </form>
      )}

      {error && <div className="error-msg mb-16">{error}</div>}

      {loading ? (
        <div style={{ color: 'var(--text-dim)', fontFamily: 'var(--font-mono)' }}>Loading...</div>
      ) : (
        <div className="grid-2">
          {users.map((u, i) => (
            <div key={u.id} className="card anim-fade" style={{ animationDelay: `${i * 0.05}s` }}>
              <div className="row-between mb-8">
                <div style={{ fontFamily: 'var(--font-title)', fontSize: '1rem', color: 'var(--text-primary)' }}>
                  {u.pseudo}
                </div>
                <span style={{ fontFamily: 'var(--font-mono)', fontSize: '.72rem', color: 'var(--text-dim)' }}>#{u.id}</span>
              </div>
              <div style={{ fontSize: '.82rem', color: 'var(--text-secondary)' }}>{u.email}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
