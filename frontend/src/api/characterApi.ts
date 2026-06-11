import URLS from './config'
import type { CharacterResponse, CharacterDetailResponse } from './types'

const base = URLS.character

export async function getCharacters(): Promise<CharacterResponse[]> {
  const res = await fetch(`${base}/characters/`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function getCharacterById(id: number): Promise<CharacterDetailResponse> {
  const res = await fetch(`${base}/characters/${id}`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function getCharactersByUser(userId: number): Promise<CharacterResponse[]> {
  const res = await fetch(`${base}/characters/by-user/${userId}`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function searchCharacters(name: string): Promise<CharacterResponse[]> {
  const res = await fetch(`${base}/characters/search?name=${encodeURIComponent(name)}`)
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}

export async function createCharacter(
  name: string,
  characterTypeName: string,
  userId: number,
): Promise<CharacterResponse> {
  const res = await fetch(`${base}/characters`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, characterTypeName, userId }),
  })
  if (!res.ok) throw new Error(await res.text())
  return res.json()
}
