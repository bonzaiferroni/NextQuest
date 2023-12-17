## Quest Selection algorithm
```kt
data class Quest(
    val id: Int = 0,
    val name: String = "my quest",
    val description: String? = null,
    val completedAt: Instant? = null,
    // val passCondition: String?,
    // val due: Instant?,
    // val repeat: Boolean = false,
    // val repeatInterval: Duration,
    // val duration: Duration,
    val superquestId: Int? = null,
    // val avoidance: Float,
    // val curiosity: Float,
    // val confidence: Float,
    // val sheetId: Int,
    // val storyId: Int,
)
```

## Next Quest Screen