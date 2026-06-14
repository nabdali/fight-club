export interface UserDTO {
  id: number
  email: string
  pseudo: string
}

export interface UserStatisticsDTO {
  victoryCounter: number
  defeatCounter: number
  bestCharacterId: number | null
  worstCharacterId: number | null
  characters: CharacterStatsDTO[]
}

export interface CharacterTypeDto {
  name: string
  strength: number
  health: number
}

export interface CharacterResponse {
  id: number
  name: string
  userId: number
  level: number
  experience: number
  createdAt: string
  characterType: CharacterTypeDto
}

export interface CharacterDetailResponse {
  userId: number
  name: string
  type: CharacterTypeDto
}

export interface FightResultDTO {
  fightId: number
  character1Id: number
  character2Id: number
  winnerId: number | null
  status: 'ENDED' | 'PENDING'
  createdAt: string
  endedAt: string | null
}

export interface UserStatisticDTO {
  id: number
  idUser: number
  idCharacter: number
  victoryCounter: number
  defeatCounter: number
}

export interface CharacterStatsDTO {
  id: number
  name: string
  type: CharacterTypeDto
  victoryCounter: number
  defeatCounter: number
}
