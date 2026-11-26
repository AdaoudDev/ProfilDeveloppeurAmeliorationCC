package com.example.tpahmaddaoudjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tpahmaddaoudjetpackcompose.ui.theme.TPAhmadDAOUDJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TPAhmadDAOUDJetpackComposeTheme {
                ProfileScreen(
                    name = "Ahmad Daoud",
                    role = "Développeur Android junior",
                    description = "Etudiant en M1 à l'ESGI, je développe des applis Android avec Kotlin et Jetpack Compose.",
                    skills = sampleSkills(),
                    isAvailable = true,
                    availability = "Disponible pour une alternance",
                    onAvailabilityClick = {
                        // affichera éventuellement la dispo
                    },
                    onContactClick = {
                        // affichera éventuellement le contact
                    }
                )
            }
        }
    }
}

// Un projet du portfolio
data class Project(
    val id: Int,
    val title: String,
    val description: String,
    val tech: String
)

// Données en dur pour l'appli, pas d'API
fun sampleProjects(): List<Project> = listOf(
    Project(
        id = 1,
        title = "Product Explorer",
        description = "Appli de catalogue produits faite pendant les TP Compose.",
        tech = "Kotlin · Jetpack Compose"
    ),
    Project(
        id = 2,
        title = "Fiche Profil",
        description = "Premier écran Compose avec une fiche de présentation.",
        tech = "Kotlin · Material 3"
    ),
    Project(
        id = 3,
        title = "Site perso",
        description = "Petit site pour présenter mes projets et mon CV.",
        tech = "HTML · CSS · Git"
    )
)

// Les compétences affichées dans la LazyRow
fun sampleSkills(): List<String> = listOf(
    "Kotlin",
    "Jetpack Compose",
    "Material 3",
    "Git",
    "Android Studio"
)

// Ecran principal de l'appli
@Composable
fun ProfileScreen(
    name: String,
    role: String,
    description: String,
    skills: List<String>,
    isAvailable: Boolean,
    availability: String,
    onAvailabilityClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        ProfileCard(
            name = name,
            role = role,
            description = description,
            skills = skills,
            isAvailable = isAvailable,
            availability = availability,
            onAvailabilityClick = onAvailabilityClick,
            onContactClick = onContactClick,
            // padding externe : c'est l'ecran qui place la fiche
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

// Fiche du profil
@Composable
fun ProfileCard(
    name: String,
    role: String,
    description: String,
    skills: List<String>,
    isAvailable: Boolean,
    availability: String,
    onAvailabilityClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        // padding interne : espace entre le bord de la fiche et le contenu
        Column(modifier = Modifier.padding(16.dp)) {
            ProfileHeader(
                name = name,
                role = role,
                description = description,
                isAvailable = isAvailable
            )
            Text(
                text = "Compétences",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // liste horizontale, un SkillChip par compétence
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(skills) { skill ->
                    SkillChip(skill = skill)
                }
            }
            AvailabilityInfo(
                text = availability,
                onClick = onAvailabilityClick,
                modifier = Modifier.padding(top = 16.dp)
            )
            ContactButton(
                text = "Contacter",
                onClick = onContactClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

// Zone cliquable pour la disponibilité
@Composable
fun AvailabilityInfo(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics {
                role = Role.Button
                contentDescription = "$text, appuyer pour plus de détails"
            },
        color = MaterialTheme.colorScheme.tertiaryContainer
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp)
        )
    }
}

// Bouton contacter, le clic est géré par le parent
@Composable
fun ContactButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

// En-tete : avatar, nom, role et description
@Composable
fun ProfileHeader(
    name: String,
    role: String,
    description: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarWithBadge(isAvailable = isAvailable)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = role,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

// Avatar rond avec le badge "Dispo" par dessus
@Composable
fun AvatarWithBadge(isAvailable: Boolean, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Photo de profil",
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
        )
        // le badge s'affiche seulement si le dev est dispo
        if (isAvailable) {
            Surface(
                modifier = Modifier.align(Alignment.BottomEnd),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.tertiary
            ) {
                Text(
                    text = "Dispo",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

// Une compétence affichée comme une petite pastille
@Composable
fun SkillChip(skill: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = skill,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

// Preview 1 : profil android junior dispo
@Preview(showBackground = true, name = "Junior Android dispo")
@Composable
fun ProfileJuniorPreview() {
    TPAhmadDAOUDJetpackComposeTheme {
        ProfileScreen(
            name = "Ahmad Daoud",
            role = "Développeur Android junior",
            description = "Etudiant en M1 à l'ESGI, je développe des applis Android avec Kotlin et Jetpack Compose.",
            skills = listOf("Kotlin", "Jetpack Compose", "Git"),
            isAvailable = true,
            availability = "Disponible pour une alternance",
            onAvailabilityClick = {},
            onContactClick = {}
        )
    }
}

// Preview 2 : profil full-stack pas dispo, avec d'autres compétences
@Preview(showBackground = true, name = "Full-stack pas dispo")
@Composable
fun ProfileFullStackPreview() {
    TPAhmadDAOUDJetpackComposeTheme {
        ProfileScreen(
            name = "Sarah Martin",
            role = "Développeuse full-stack",
            description = "5 ans d'expérience en web et mobile, surtout sur des API et du React.",
            skills = listOf("TypeScript", "React", "Node.js", "PostgreSQL", "Docker"),
            isAvailable = false,
            availability = "Pas disponible, en mission jusqu'en mars 2027",
            onAvailabilityClick = {},
            onContactClick = {}
        )
    }
}
