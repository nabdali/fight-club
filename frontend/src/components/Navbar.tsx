import { NavLink } from 'react-router-dom'
import { useEvents } from '../context/EventContext'

const NAV = [
  { to: '/arena',       icon: '⚔️',  label: 'Arena' },
  { to: '/users',       icon: '👤',  label: 'Users' },
  { to: '/characters',  icon: '🎭',  label: 'Characters' },
  { to: '/leaderboard', icon: '🏆',  label: 'Leaderboard' },
  { to: '/feed',        icon: '📡',  label: 'Live Feed' },
]

export default function Navbar() {
  const { events } = useEvents()
  const unread = events.length

  return (
    <nav style={{
      background: 'rgba(10,10,20,.95)',
      borderBottom: '1px solid rgba(255,0,60,.2)',
      boxShadow: '0 2px 20px rgba(255,0,60,.08)',
      backdropFilter: 'blur(10px)',
      position: 'sticky', top: 0, zIndex: 100,
    }}>
      <div style={{
        maxWidth: 1300, margin: '0 auto',
        padding: '0 24px',
        display: 'flex', alignItems: 'center', gap: 8, height: 60,
      }}>
        <span style={{
          fontFamily: 'var(--font-title)',
          fontSize: '1.1rem',
          color: 'var(--accent-red)',
          textShadow: '0 0 14px rgba(255,0,60,.6)',
          letterSpacing: '.12em',
          marginRight: 24,
          whiteSpace: 'nowrap',
        }}>
          ☠ FIGHT CLUB
        </span>

        <div style={{ display: 'flex', gap: 4, flex: 1 }}>
          {NAV.map(({ to, icon, label }) => (
            <NavLink
              key={to}
              to={to}
              style={({ isActive }) => ({
                display: 'flex', alignItems: 'center', gap: 6,
                padding: '6px 14px',
                borderRadius: 'var(--r)',
                textDecoration: 'none',
                fontFamily: 'var(--font-ui)',
                fontWeight: 600,
                fontSize: '.82rem',
                letterSpacing: '.07em',
                textTransform: 'uppercase',
                transition: 'all .15s',
                color:      isActive ? 'var(--accent-gold)' : 'var(--text-secondary)',
                background: isActive ? 'rgba(255,215,0,.08)' : 'transparent',
                borderBottom: isActive ? '2px solid var(--accent-gold)' : '2px solid transparent',
                position: to === '/feed' ? 'relative' : undefined,
              })}
            >
              <span>{icon}</span>
              <span>{label}</span>
              {to === '/feed' && unread > 0 && (
                <span style={{
                  position: 'absolute', top: 2, right: 2,
                  background: 'var(--accent-red)',
                  color: '#fff',
                  fontSize: '.6rem', fontWeight: 700,
                  borderRadius: '50%', width: 16, height: 16,
                  display: 'flex', alignItems: 'center', justifyContent: 'center',
                  boxShadow: '0 0 6px rgba(255,0,60,.6)',
                }}>
                  {unread > 99 ? '99' : unread}
                </span>
              )}
            </NavLink>
          ))}
        </div>
      </div>
    </nav>
  )
}
