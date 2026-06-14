import { createContext, useContext, useState, useCallback, type ReactNode } from 'react'

export type EventType =
  | 'FIGHT_STARTED'
  | 'FIGHT_JOINED'
  | 'FIGHT_ENDED'
  | 'CHARACTER_CREATED'
  | 'USER_REGISTERED'
  | 'LEADERBOARD_UPDATE'
  | 'ERROR'

export interface AppEvent {
  id: string
  type: EventType
  topic: string
  message: string
  data?: unknown
  timestamp: Date
}

interface EventContextValue {
  events: AppEvent[]
  publish: (type: EventType, topic: string, message: string, data?: unknown) => void
}

const EventContext = createContext<EventContextValue | null>(null)

export function EventProvider({ children }: { children: ReactNode }) {
  const [events, setEvents] = useState<AppEvent[]>([])

  const publish = useCallback((type: EventType, topic: string, message: string, data?: unknown) => {
    const event: AppEvent = {
      id: Math.random().toString(36).slice(2),
      type,
      topic,
      message,
      data,
      timestamp: new Date(),
    }
    setEvents(prev => [event, ...prev].slice(0, 100))
  }, [])

  return <EventContext.Provider value={{ events, publish }}>{children}</EventContext.Provider>
}

export function useEvents() {
  const ctx = useContext(EventContext)
  if (!ctx) throw new Error('useEvents must be used within EventProvider')
  return ctx
}
