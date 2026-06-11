import { useState } from 'react'
import { startFight, joinFight, getFightResult } from '../api/arenaApi'
import { getCharacterById } from '../api/characterApi'
import type { FightResultDTO, CharacterDetailResponse } from '../api/types'
import { useEvents } from '../context/EventContext'

type Phase = 'idle' | 'waiting' | 'result'

const TYPE_ICON: Record<string, string> = {
  Assassin: '🗡️', Mage: '🔮', Tank: '🛡️', Archer: '🏹',
}

function FighterCard({ char, charId, label, isWinner, isLoser }: {
  char: CharacterDetailResponse | null
  charId: string
  label: string
  isWinner?: boolean
  isLoser?: boolean
}) {
  const style: React.CSSProperties = {
    flex: 1, padding: 24, borderRadius: 'var(--r-lg)', textAlign: 'center',
    border: `1px solid ${isWinner ? 'rgba(255,215,0,.5)' : isLoser ? 'rgba(255,0,60,.3)' : 'var(--border)'}`,
    background: isWinner ? 'rgba(255,215,0,.06)' : isLoser ? 'rgba(255,0,60,.04)' : 'var(--bg-card)',
    transition: 'all .4s',
  }

  if (!char) return (
    <div style={style}>
      <div style={{ fontSize: '3rem', marginBottom: 8, opacity: .2 }}>?</div>
      <div style={{ color: 'var(--text-dim)', fontFamily: 'var(--font-title)', fontSize: '.8rem' }}>{label}</div>
      {charId && <div style={{ color: 'var(--text-dim)', fontFamily: 'var(--font-mono)', fontSize: '.75rem', marginTop: 4 }}>ID #{charId}</div>}
    </div>
  )

  const typeName = char.type?.name ?? ''

  return (
    <div className={isWinner ? 'anim-gold' : ''} style={style}>
      {isWinner && <div style={{ fontSize: '.9rem', color: 'var(--accent-gold)', fontFamily: 'var(--font-title)', marginBottom: 8, letterSpacing: '.1em' }}>👑 WINNER</div>}
      {isLoser  && <div style={{ fontSize: '.9rem', color: 'var(--accent-red)',  fontFamily: 'var(--font-title)', marginBottom: 8, letterSpacing: '.1em' }}>💀 DEFEATED</div>}
      <div style={{ fontSize: '2.8rem', marginBottom: 6 }}>{TYPE_ICON[typeName] ?? '⚔️'}</div>
      <div style={{ fontFamily: 'var(--font-title)', fontSize: '1.1rem', color: isWinner ? 'var(--accent-gold)' : 'var(--text-primary)', marginBottom: 6 }}>
        {char.name}
      </div>
      <span className={`badge badge-${typeName}`}>{typeName}</span>
      <div style={{ marginTop: 14 }} className="col gap-4">
        <div className="stat-bar">
          <div className="stat-bar-label"><span>HP</span><span>{char.type?.health}</span></div>
          <div className="stat-bar-track"><div className="stat-bar-fill-hp" style={{ width: `${Math.min(100, (char.type?.health ?? 0) / 1.5)}%` }} /></div>
        </div>
        <div className="stat-bar">
          <div className="stat-bar-label"><span>STR</span><span>{char.type?.strength}</span></div>
          <div className="stat-bar-track"><div className="stat-bar-fill-str" style={{ width: `${Math.min(100, char.type?.strength ?? 0)}%` }} /></div>
        </div>
      </div>
      <div style={{ marginTop: 10, fontSize: '.78rem', color: 'var(--text-dim)', fontFamily: 'var(--font-mono)' }}>
        ID #{charId}
      </div>
    </div>
  )
}

export default function Arena() {
  const { publish } = useEvents()
  const [char1Id, setChar1Id] = useState('')
  const [char2Id, setChar2Id] = useState('')
  const [char1, setChar1] = useState<CharacterDetailResponse | null>(null)
  const [char2, setChar2] = useState<CharacterDetailResponse | null>(null)
  const [fightId, setFightId] = useState<number | null>(null)
  const [result, setResult] = useState<FightResultDTO | null>(null)
  const [phase, setPhase] = useState<Phase>('idle')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  async function handleStart() {
    if (!char1Id) return
    setError(''); setLoading(true)
    try {
      const [char, { fightId: id }] = await Promise.all([
        getCharacterById(Number(char1Id)),
        startFight(Number(char1Id)),
      ])
      setChar1(char); setFightId(id); setPhase('waiting')
      publish('FIGHT_STARTED', 'arena-events',
        `Fight #${id} started — Fighter: ${char.name} (${char.type?.name})`,
        { fightId: id, characterId: char1Id })
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : 'Error')
    } finally { setLoading(false) }
  }

  async function handleJoin() {
    if (!char2Id || !fightId) return
    setError(''); setLoading(true)
    try {
      const [char] = await Promise.all([
        getCharacterById(Number(char2Id)),
        joinFight(Number(char2Id)),
      ])
      const res = await getFightResult(fightId)
      setChar2(char); setResult(res); setPhase('result')
      const winnerName = res.winnerId === Number(char1Id) ? char1?.name : char.name
      publish('FIGHT_ENDED', 'arena-events',
        `Fight #${fightId} ended — Winner: ${winnerName} (#${res.winnerId})`,
        { fightId, winnerId: res.winnerId })
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : 'Error')
    } finally { setLoading(false) }
  }

  function reset() {
    setChar1Id(''); setChar2Id(''); setChar1(null); setChar2(null)
    setFightId(null); setResult(null); setPhase('idle'); setError('')
  }

  const winner1 = result != null && result.winnerId === Number(char1Id)
  const winner2 = result != null && result.winnerId === Number(char2Id)
  const winnerName = winner1 ? char1?.name : winner2 ? char2?.name : null

  return (
    <div>
      <div className="row-between mb-24">
        <h1 className="page-title" style={{ marginBottom: 0 }}>⚔ Arena</h1>
        {phase !== 'idle' && (
          <button className="btn btn-outline" onClick={reset}>New Fight</button>
        )}
      </div>

      {/* Fighter cards */}
      <div style={{ display: 'flex', gap: 20, alignItems: 'stretch', marginBottom: 24 }}>
        <FighterCard
          char={char1} charId={char1Id} label="Fighter 1"
          isWinner={winner1} isLoser={result != null && !winner1 && char1 != null}
        />
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', gap: 8, minWidth: 60 }}>
          <div style={{ fontFamily: 'var(--font-title)', fontSize: '2rem', color: 'var(--accent-red)', textShadow: '0 0 20px rgba(255,0,60,.6)' }}>VS</div>
          {fightId && <div style={{ fontFamily: 'var(--font-mono)', fontSize: '.7rem', color: 'var(--text-dim)' }}>#{fightId}</div>}
        </div>
        <FighterCard
          char={char2} charId={char2Id} label="Fighter 2"
          isWinner={winner2} isLoser={result != null && !winner2 && char2 != null}
        />
      </div>

      {/* Result banner */}
      {result && winnerName && (
        <div className="anim-pop card card--gold mb-24" style={{ textAlign: 'center', padding: 28 }}>
          <div style={{ fontFamily: 'var(--font-title)', fontSize: '1.6rem', color: 'var(--accent-gold)', textShadow: '0 0 30px rgba(255,215,0,.7)', marginBottom: 8 }}>
            👑 {winnerName} WINS!
          </div>
          <div style={{ color: 'var(--text-secondary)', fontFamily: 'var(--font-mono)', fontSize: '.82rem' }}>
            Fight #{result.fightId} · {new Date(result.createdAt).toLocaleTimeString()}
          </div>
        </div>
      )}

      {/* Control panel */}
      {phase !== 'result' && (
        <div className="card" style={{ maxWidth: 540 }}>
          <p className="section-title">{phase === 'idle' ? '1 — Challenge' : '2 — Accept Challenge'}</p>

          {phase === 'idle' && (
            <div className="col">
              <input className="input" type="number" placeholder="Fighter 1 character ID"
                value={char1Id} onChange={e => setChar1Id(e.target.value)}
                onKeyDown={e => e.key === 'Enter' && handleStart()} />
              <button className="btn btn-red w-full" onClick={handleStart} disabled={!char1Id || loading}>
                {loading ? 'Challenging...' : '⚔ Challenge'}
              </button>
            </div>
          )}

          {phase === 'waiting' && (
            <div className="col">
              <div style={{ color: 'var(--accent-gold)', fontFamily: 'var(--font-mono)', fontSize: '.82rem', marginBottom: 4 }}>
                ● Fight #{fightId} open — waiting for opponent
              </div>
              <input className="input" type="number" placeholder="Fighter 2 character ID"
                value={char2Id} onChange={e => setChar2Id(e.target.value)}
                onKeyDown={e => e.key === 'Enter' && handleJoin()} />
              <button className="btn btn-gold w-full" onClick={handleJoin} disabled={!char2Id || loading}>
                {loading ? 'Joining...' : '🏟 Enter the Arena'}
              </button>
            </div>
          )}

          {error && <div className="error-msg mt-8">{error}</div>}
        </div>
      )}
    </div>
  )
}
