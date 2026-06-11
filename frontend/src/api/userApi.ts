import URLS from './config'
import type { UserDTO, UserStatisticsDTO } from './types'

const base = URLS.user

export async function getUsers(): Promise<UserDTO[]> {
  const res = await fetch(`${base}/users`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function register(email: string, pseudo: string, password: string): Promise<UserDTO> {
  const res = await fetch(`${base}/users/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, pseudo, password }),
  })
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function login(pseudo: string, password: string): Promise<number> {
  const res = await fetch(`${base}/users/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ pseudo, password }),
  })
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function getUserStats(userId: number): Promise<UserStatisticsDTO> {
  const res = await fetch(`${base}/users/${userId}/stats`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}
