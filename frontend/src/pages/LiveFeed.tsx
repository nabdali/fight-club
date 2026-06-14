import { useEffect, useRef, useState } from 'react'
import { useEvents, type AppEvent, type EventType } from '../context/EventContext'
import URLS from '../api/config'

const TOPIC_TO_EVENT: Record<string, EventType> = {
  'fight.created': 'FIGHT_STARTED',
  'fight.ended':   'FIGHT_ENDED',
  'stats.update':  'LEADERBOARD_UPDATE',
}

const EVENT_COLOR: Record<string, string> = {
  FIGHT_STARTED:      '#fbbf24',
  FIGHT_JOINED:       '#60a5fa',
  FIGHT_ENDED:        '#f87171',
  CHARACTER_CREATED:  '#a78bfa',
  USER_REGISTERED:    '#34d399',
  LEADERBOARD_UPDATE: '#38bdf8',
  ERROR:              '#ff003c',
}

const TOPIC_COLOR: Record<string, string> = {
  'fight.created':    '#f87171',
  'fight.ended':      '#fb923c',
  'stats.update':     '#38bdf8',
  'arena-events':     '#f87171',
  'character-events': '#a78bfa',
  'user-events':      '#34d399',
}

function fmt(d: Date) { return d.toTimeString().slice(0, 8) }

function EventRow({ event }: { event: AppEvent }) {
  const color = EVENT_COLOR[event.type] ?? '#fff'
  const topicColor = TOPIC_COLOR[event.topic] ?? 'var(--text-dim)'

  return (
    <div className="anim-scan" style={{
      display: 'flex', gap: 12, alignItems: 'flex-start',
      padding: '7px 0',
      borderBottom: '1px solid rgba(255,255,255,.04)',
      fontFamily: 'var(--font-mono)',
      fontSize: '.78rem',
    }}>
      <span style={{ color: 'var(--text-dim)', minWidth: 70, flexShrink: 0 }}>{fmt(event.timestamp)}</span>
      <span style={{ color: topicColor, minWidth: 160, flexShrink: 0 }}>{event.topic}</span>
      <span style={{ color, minWidth: 180, flexShrink: 0, fontWeight: 700 }}>{event.type}</span>
      <span style={{ color: 'var(--text-secondary)', flex: 1, wordBreak: 'break-all' }}>{event.message}</span>
    </div>
  )
}

type Status = 'connecting' | 'connected' | 'error'

export default function LiveFeed() {
  const { events, publish } = useEvents()
  const esRef = useRef<EventSource | null>(null)
  const [status, setStatus] = useState<Status>('connecting')

  useEffect(() => {
    const url = `${URLS.gateway}/events/stream`
    const es = new EventSource(url)
    esRef.current = es

    es.onopen = () => setStatus('connected')
    es.onerror = () => setStatus('error')

    const topics = ['fight.created', 'fight.ended', 'stats.update'] as const

    topics.forEach(topic => {
      es.addEventListener(topic, (e: MessageEvent) => {
        const type = TOPIC_TO_EVENT[topic]
        let message = e.data as string
        try {
          const parsed = JSON.parse(e.data as string)
          if (topic === 'fight.created') message = `Fight #${parsed.fightId} started — char1: #${parsed.character1Id}`
          if (topic === 'fight.ended')   message = `Fight #${parsed.fightId} ended — winner: #${parsed.winnerId}`
          if (topic === 'stats.update')  message = `Stats updated — winner: #${parsed.winnerId}, loser: #${parsed.looserId}`
        } catch { /* keep raw */ }
        publish(type, topic, message, e.data)
      })
    })

    return () => es.close()
  }, [publish])

  const statusDot = {
    connecting: { color: '#fbbf24', label: 'CONNECTING' },
    connected:  { color: '#27c93f', label: 'LIVE' },
    error:      { color: '#ff003c', label: 'ERROR' },
  }[status]

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: 'calc(100vh - 160px)' }}>
      <div className="row-between mb-16">
        <h1 className="page-title" style={{ marginBottom: 0 }}>📡 Live Feed</h1>
        <div style={{ fontFamily: 'var(--font-mono)', fontSize: '.75rem', color: 'var(--text-dim)' }}>
          {events.length} messages
        </div>
      </div>

      {/* Terminal header */}
      <div style={{
        background: 'rgba(0,0,0,.5)',
        border: '1px solid rgba(0,229,255,.2)',
        borderBottom: 'none',
        borderRadius: 'var(--r-lg) var(--r-lg) 0 0',
        padding: '10px 16px',
        display: 'flex', alignItems: 'center', gap: 16,
      }}>
        <div className="row" style={{ gap: 6 }}>
          <div style={{ width: 10, height: 10, borderRadius: '50%', background: '#ff5f56' }} />
          <div style={{ width: 10, height: 10, borderRadius: '50%', background: '#ffbd2e' }} />
          <div style={{ width: 10, height: 10, borderRadius: '50%', background: '#27c93f' }} />
        </div>
        <div style={{ fontFamily: 'var(--font-mono)', fontSize: '.78rem', color: 'var(--accent-cyan)', flex: 1 }}>
          <span style={{ color: 'var(--text-dim)' }}>EventSource → </span>
          <span style={{ color: 'var(--accent-cyan)' }}>{URLS.gateway}/events/stream</span>
          <span style={{ color: 'var(--text-dim)' }}> — topics: </span>
          <span style={{ color: '#f87171' }}>fight.created</span>
          <span style={{ color: 'var(--text-dim)' }}>, </span>
          <span style={{ color: '#fb923c' }}>fight.ended</span>
          <span style={{ color: 'var(--text-dim)' }}>, </span>
          <span style={{ color: '#38bdf8' }}>stats.update</span>
        </div>
        <div style={{ fontFamily: 'var(--font-mono)', fontSize: '.72rem', display: 'flex', alignItems: 'center', gap: 6 }}>
          <span style={{
            display: 'inline-block', width: 7, height: 7, borderRadius: '50%',
            background: statusDot.color,
            boxShadow: `0 0 6px ${statusDot.color}`,
            animation: status === 'connected' ? 'blink 1.2s ease infinite' : undefined,
          }} />
          <span style={{ color: statusDot.color }}>{statusDot.label}</span>
        </div>
      </div>

      {/* Column headers */}
      <div style={{
        background: 'rgba(0,0,0,.4)',
        border: '1px solid rgba(0,229,255,.2)',
        borderTop: '1px solid rgba(255,255,255,.06)',
        borderBottom: 'none',
        padding: '5px 16px',
        display: 'flex', gap: 12,
        fontFamily: 'var(--font-mono)', fontSize: '.7rem', color: 'var(--text-dim)',
      }}>
        <span style={{ minWidth: 70 }}>TIME</span>
        <span style={{ minWidth: 160 }}>TOPIC</span>
        <span style={{ minWidth: 180 }}>EVENT</span>
        <span>PAYLOAD</span>
      </div>

      {/* Events */}
      <div style={{
        flex: 1,
        background: 'rgba(0,0,0,.45)',
        border: '1px solid rgba(0,229,255,.2)',
        borderTop: 'none',
        borderRadius: '0 0 var(--r-lg) var(--r-lg)',
        padding: '0 16px',
        overflowY: 'auto',
      }}>
        {events.length === 0 ? (
          <div style={{
            display: 'flex', alignItems: 'center', justifyContent: 'center', height: '100%',
            color: 'var(--text-dim)', fontFamily: 'var(--font-mono)', fontSize: '.82rem',
            flexDirection: 'column', gap: 8,
          }}>
            <span>Waiting for Kafka events...</span>
            {status === 'error' && (
              <span style={{ color: 'var(--accent-red)', fontSize: '.75rem' }}>
                Cannot connect to {URLS.gateway}/events/stream — check gateway service
              </span>
            )}
          </div>
        ) : (
          events.map(e => <EventRow key={e.id} event={e} />)
        )}
      </div>
    </div>
  )
}
