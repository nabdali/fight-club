import URLS from './config'
import type { FightResultDTO } from './types'

const base = URLS.arena

export async function startFight(characterId: number): Promise<{ fightId: number }> {
  const res = await fetch(`${base}/arena/start/${characterId}`, { method: 'POST' })
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function joinFight(characterId: number): Promise<{ fightId: number }> {
  const res = await fetch(`${base}/arena/join/${characterId}`, { method: 'POST' })
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function getFightResult(fightId: number): Promise<FightResultDTO> {
  const res = await fetch(`${base}/arena/${fightId}/result`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}
