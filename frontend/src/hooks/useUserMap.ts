import { useState, useEffect } from 'react'
import { getUsers } from '../api/userApi'

// Module-level cache — survives re-renders and component unmounts
let cache: Map<number, string> | null = null
let pending: Promise<Map<number, string>> | null = null

async function fetchUserMap(): Promise<Map<number, string>> {
  if (cache) return cache
  if (!pending) {
    pending = getUsers().then(users => {
      cache = new Map(users.map(u => [u.id, u.pseudo]))
      return cache
    })
  }
  return pending
}

export function useUserMap() {
  const [userMap, setUserMap] = useState<Map<number, string>>(cache ?? new Map())

  useEffect(() => {
    if (cache) return
    fetchUserMap().then(setUserMap).catch(() => {})
  }, [])

  return (id: number | undefined): string =>
    id !== undefined ? (userMap.get(id) ?? `#${id}`) : '—'
}
