import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { EventProvider } from './context/EventContext'
import Navbar from './components/Navbar'
import Arena from './pages/Arena'
import Users from './pages/Users'
import Characters from './pages/Characters'
import Leaderboard from './pages/Leaderboard'
import LiveFeed from './pages/LiveFeed'

export default function App() {
  return (
    <EventProvider>
      <BrowserRouter>
        <div className="app">
          <Navbar />
          <main className="main-content">
            <Routes>
              <Route path="/" element={<Navigate to="/arena" replace />} />
              <Route path="/arena"       element={<Arena />} />
              <Route path="/users"       element={<Users />} />
              <Route path="/characters"  element={<Characters />} />
              <Route path="/leaderboard" element={<Leaderboard />} />
              <Route path="/feed"        element={<LiveFeed />} />
            </Routes>
          </main>
        </div>
      </BrowserRouter>
    </EventProvider>
  )
}
