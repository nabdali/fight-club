import { useEffect, useState } from 'react'
import { getMostVictories, getMostDefeats } from '../api/leaderboardApi'
import type { UserStatisticDTO } from '../api/types'
import { useUserMap } from '../hooks/useUserMap'

type Tab = 'victories' | 'defeats'

const MEDALS = ['🥇', '🥈', '🥉']

export default function Leaderboard() {
  const username = useUserMap()
  const [tab, setTab] = useState<Tab>('victories')
  const [data, setData] = useState<UserStatisticDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  async function load(t: Tab) {
    setLoading(true); setError('')
    try {
      setData(t === 'victories' ? await getMostVictories() : await getMostDefeats())
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : 'Error')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { load(tab) }, [tab])

  const maxVal = data.reduce((m, d) => Math.max(m, tab === 'victories' ? (d.victoryCounter ?? 0) : (d.defeatCounter ?? 0)), 1)

  return (
    <div>
      <h1 className="page-title">🏆 Leaderboard</h1>

      {/* Tabs */}
      <div className="row mb-24" style={{ gap: 8 }}>
        <button
          className={`btn ${tab === 'victories' ? 'btn-gold' : 'btn-outline'}`}
          onClick={() => setTab('victories')}
        >⚔ Most Victories</button>
        <button
          className={`btn ${tab === 'defeats' ? 'btn-red' : 'btn-outline'}`}
          onClick={() => setTab('defeats')}
        >💀 Most Defeats</button>
      </div>

      {error && <div className="error-msg mb-16">{error}</div>}

      {loading ? (
        <div style={{ color: 'var(--text-dim)', fontFamily: 'var(--font-mono)' }}>Loading rankings...</div>
      ) : data.length === 0 ? (
        <div style={{ color: 'var(--text-dim)', fontFamily: 'var(--font-mono)' }}>No data yet.</div>
      ) : (
        <div className="col gap-8">
          {data.map((row, i) => {
            const val = tab === 'victories' ? (row.victoryCounter ?? 0) : (row.defeatCounter ?? 0)
            const pct = maxVal > 0 ? (val / maxVal) * 100 : 0
            const isTop = i < 3
            const accent = tab === 'victories' ? 'var(--accent-gold)' : 'var(--accent-red)'
            const fillColor = tab === 'victories'
              ? 'linear-gradient(90deg,#b8860b,#ffd700)'
              : 'linear-gradient(90deg,#7f0020,#ff003c)'

            return (
              <div key={row.id} className="card anim-fade" style={{
                animationDelay: `${i * 0.05}s`,
                borderColor: isTop ? `${accent}40` : undefined,
                background: isTop ? `rgba(${tab === 'victories' ? '255,215,0' : '255,0,60'},.04)` : undefined,
                padding: '16px 20px',
              }}>
                <div className="row-between mb-8">
                  <div className="row" style={{ gap: 12 }}>
                    <span style={{ fontSize: '1.4rem', minWidth: 30 }}>{MEDALS[i] ?? `#${i + 1}`}</span>
                    <div>
                      <div style={{ fontFamily: 'var(--font-title)', fontSize: '.95rem' }}>
                        {username(row.idUser)}
                      </div>
                      <div style={{ fontSize: '.75rem', color: 'var(--text-dim)', fontFamily: 'var(--font-mono)' }}>
                        Character #{row.idCharacter}
                      </div>
                    </div>
                  </div>
                  <div style={{ fontFamily: 'var(--font-title)', fontSize: '1.3rem', color: accent, textShadow: `0 0 12px ${accent}80` }}>
                    {val}
                    <span style={{ fontSize: '.7rem', marginLeft: 4, color: 'var(--text-dim)' }}>
                      {tab === 'victories' ? 'W' : 'L'}
                    </span>
                  </div>
                </div>
                <div style={{ height: 5, background: 'rgba(255,255,255,.05)', borderRadius: 3, overflow: 'hidden' }}>
                  <div style={{ height: '100%', width: `${pct}%`, borderRadius: 3, background: fillColor, transition: 'width .8s ease' }} />
                </div>
              </div>
            )
          })}
        </div>
      )}
    </div>
  )
}
