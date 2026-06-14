import URLS from './config'
import type { UserStatisticDTO, CharacterStatsDTO } from './types'

const base = URLS.leaderboard

export async function getMostVictories(): Promise<UserStatisticDTO[]> {
  const res = await fetch(`${base}/leaderboard/most-victories`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function getMostDefeats(): Promise<UserStatisticDTO[]> {
  const res = await fetch(`${base}/leaderboard/most-defeats`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function getVictoriesByCharacter(id: number): Promise<UserStatisticDTO[]> {
  const res = await fetch(`${base}/leaderboard/most-victories-character/${id}`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function getUserCharacterStats(userId: number): Promise<CharacterStatsDTO[]> {
  const res = await fetch(`${base}/leaderboard/user/${userId}/characters`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}
