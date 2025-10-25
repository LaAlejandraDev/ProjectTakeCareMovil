package com.example.takecare.data.exampleData

import com.example.takecare.data.models.*

val sampleUsers = listOf(
    User(
        id = 1,
        name = "Juan",
        lastName = "Pérez",
        email = "juanperez@example.com",
        phone = "555-123-4567",
        userName = "juanp",
        password = "12345",
        imageUrl = null,
        type = UserType.PATIENT,
        isActive = true,
        createdAt = "2025-09-10",
        updatedAt = "2025-10-10",
        deletedAt = ""
    ),
    User(
        id = 2,
        name = "María",
        lastName = "López",
        email = "maria.lopez@example.com",
        phone = "555-987-6543",
        userName = "mlopez",
        password = "abcd1234",
        imageUrl = null,
        type = UserType.PSICOLOGIST,
        isActive = true,
        createdAt = "2025-09-05",
        updatedAt = "2025-10-15",
        deletedAt = ""
    ),
    User(
        id = 3,
        name = "Carlos",
        lastName = "Ramírez",
        email = "carlosr@example.com",
        phone = "555-222-3344",
        userName = "cramirez",
        password = "mypassword",
        imageUrl = null,
        type = UserType.ADMIN,
        isActive = true,
        createdAt = "2025-08-30",
        updatedAt = "2025-10-22",
        deletedAt = ""
    )
)

val samplePosts = mutableListOf<Post?>(
    Post(
        id = 1,
        title = "¿Cómo manejar la ansiedad en exámenes?",
        content = "Últimamente he tenido mucha ansiedad antes de mis exámenes. ¿Algún consejo o técnica para controlarla?",
        date = "2025-10-10",
        type = PostType.QUESTION,
        user = sampleUsers[0],
        likesCount = 12,
        commentCount = 3
    ),
    Post(
        id = 2,
        title = "Recordatorio: Nueva sesión grupal el viernes",
        content = "Este viernes tendremos una sesión grupal sobre manejo del estrés. ¡No falten!",
        date = "2025-10-12",
        type = PostType.ANNOUNCEMENT,
        user = sampleUsers[2],
        likesCount = 20,
        commentCount = 4
    ),
    Post(
        id = 3,
        title = "Pequeñas victorias",
        content = "Hoy logré levantarme temprano, hacer ejercicio y meditar. Me siento orgulloso del progreso.",
        date = "2025-10-15",
        type = PostType.DISCUSSION,
        user = sampleUsers[1],
        likesCount = 8,
        commentCount = 2
    ),
    Post(
        id = 4,
        title = "Overwatch me cabio la vida (para mal)",
        content = "Hoy logré ganar 3 rankes pero a que costo.",
        date = "2025-10-15",
        type = PostType.DISCUSSION,
        user = sampleUsers[1],
        likesCount = 8,
        commentCount = 2
    )
)

val sampleComments = mutableListOf<Comment?>(
    Comment(
        id = 1,
        postId = 1,
        date = "2025-10-11",
        content = "Practica la respiración 4-7-8, a mí me ayuda mucho antes de los exámenes.",
        user = sampleUsers[1],
        likes = 5
    ),
    Comment(
        id = 2,
        postId = 1,
        date = "2025-10-11",
        content = "Yo también paso por eso, intento no estudiar justo antes del examen y descansar bien.",
        user = sampleUsers[2],
        likes = 3
    ),
    Comment(
        id = 3,
        postId = 3,
        date = "2025-10-16",
        content = "¡Qué bien! Es importante reconocer esos logros, felicidades 💪",
        user = sampleUsers[0],
        likes = 4
    )
)
