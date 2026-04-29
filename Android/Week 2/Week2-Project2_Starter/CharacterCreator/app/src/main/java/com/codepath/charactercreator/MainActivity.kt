// Navigation hub. Read this to understand how the app moves between screens.
//
// How Compose Navigation works in this app:
//   1. Each screen is registered with composable("route") { ... }
//   2. When a screen is done, it calls onXxxSelected(...) with its result.
//   3. This file catches that result and navigates forward by building a route string.
//   4. The next screen receives its data by extracting it from backStackEntry.arguments.
//
// Route strings work like URLs:
//   Template:  "weapon/{characterClass}/{name}/..."
//   Navigate:  navController.navigate("weapon/$characterClass/$name/...")
//   Extract:   backStackEntry.arguments?.getString("characterClass")
//
// All selections travel as plain strings in the route — there is no shared ViewModel.
package com.codepath.charactercreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {

                // Screen 1: no arguments needed — it's the start
                composable("home") {
                    HomeScreen(
                        onBeginClicked = { navController.navigate("face") }
                    )
                }

                // Screen 2: face fields (including name) become route segments for every screen after this
                composable("face") {
                    FaceScreen(
                        onFaceSelected = { face ->
                            navController.navigate(
                                "class/${face.name}/${face.skinTone}/${face.eyeColor}/${face.hairStyle}/${face.hairColor}/${face.earType}"
                            )
                        }
                    )
                }

                // Screen 3: extracts the face fields and passes characterClass forward
                composable("class/{name}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val name      = back.arguments?.getString("name")      ?: ""
                    val skin      = back.arguments?.getString("skin")      ?: ""
                    val eye       = back.arguments?.getString("eye")       ?: ""
                    val hairStyle = back.arguments?.getString("hairStyle") ?: ""
                    val hairColor = back.arguments?.getString("hairColor") ?: ""
                    val ear       = back.arguments?.getString("ear")       ?: ""
                    ClassScreen(
                        onClassSelected = { characterClass ->
                            navController.navigate("weapon/$characterClass/$name/$skin/$eye/$hairStyle/$hairColor/$ear")
                        }
                    )
                }

                // Screen 4: uses characterClass to show class-specific weapons (this is where Bug 1 lives in WeaponScreen.kt)
                composable("weapon/{characterClass}/{name}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val characterClass = back.arguments?.getString("characterClass") ?: ""
                    val name           = back.arguments?.getString("name")           ?: ""
                    val skin           = back.arguments?.getString("skin")           ?: ""
                    val eye            = back.arguments?.getString("eye")            ?: ""
                    val hairStyle      = back.arguments?.getString("hairStyle")      ?: ""
                    val hairColor      = back.arguments?.getString("hairColor")      ?: ""
                    val ear            = back.arguments?.getString("ear")            ?: ""
                    WeaponScreen(
                        characterClass = characterClass,
                        onWeaponSelected = { weapon ->
                            navController.navigate("stat/$characterClass/$weapon/$name/$skin/$eye/$hairStyle/$hairColor/$ear")
                        }
                    )
                }

                // Screen 5: Confirm button in StatScreen.kt has a TODO — the onStatSelected lambda here is pre-built
                composable("stat/{characterClass}/{weapon}/{name}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val characterClass = back.arguments?.getString("characterClass") ?: ""
                    val weapon         = back.arguments?.getString("weapon")         ?: ""
                    val name           = back.arguments?.getString("name")           ?: ""
                    val skin           = back.arguments?.getString("skin")           ?: ""
                    val eye            = back.arguments?.getString("eye")            ?: ""
                    val hairStyle      = back.arguments?.getString("hairStyle")      ?: ""
                    val hairColor      = back.arguments?.getString("hairColor")      ?: ""
                    val ear            = back.arguments?.getString("ear")            ?: ""
                    StatScreen(
                        characterClass = characterClass,
                        weapon = weapon,
                        onStatSelected = { stat ->
                            navController.navigate("ability/$characterClass/$stat/$weapon/$name/$skin/$eye/$hairStyle/$hairColor/$ear")
                        }
                    )
                }

                // Screen 6: complete — no student changes needed here
                composable("ability/{characterClass}/{stat}/{weapon}/{name}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val characterClass = back.arguments?.getString("characterClass") ?: ""
                    val stat           = back.arguments?.getString("stat")           ?: ""
                    val weapon         = back.arguments?.getString("weapon")         ?: ""
                    val name           = back.arguments?.getString("name")           ?: ""
                    val skin           = back.arguments?.getString("skin")           ?: ""
                    val eye            = back.arguments?.getString("eye")            ?: ""
                    val hairStyle      = back.arguments?.getString("hairStyle")      ?: ""
                    val hairColor      = back.arguments?.getString("hairColor")      ?: ""
                    val ear            = back.arguments?.getString("ear")            ?: ""
                    AbilityScreen(
                        characterClass = characterClass,
                        stat = stat,
                        weapon = weapon,
                        onAbilitySelected = { ability ->
                            navController.navigate("card/$characterClass/$stat/$weapon/$ability/$name/$skin/$eye/$hairStyle/$hairColor/$ear")
                        }
                    )
                }

                // Screen 7: all selections arrive here — CharacterCard has display TODOs in CharacterCardScreen.kt
                composable("card/{characterClass}/{stat}/{weapon}/{ability}/{name}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val characterClass = back.arguments?.getString("characterClass") ?: ""
                    val stat           = back.arguments?.getString("stat")           ?: ""
                    val weapon         = back.arguments?.getString("weapon")         ?: ""
                    val ability        = back.arguments?.getString("ability")        ?: ""
                    // TODO: Extract the character's name from the route arguments here.
                    // Follow the same pattern used for `characterClass` above.
                    val name           = ""
                    val skin           = back.arguments?.getString("skin")           ?: ""
                    val eye            = back.arguments?.getString("eye")            ?: ""
                    val hairStyle      = back.arguments?.getString("hairStyle")      ?: ""
                    val hairColor      = back.arguments?.getString("hairColor")      ?: ""
                    val ear            = back.arguments?.getString("ear")            ?: ""
                    CharacterCardScreen(
                        characterClass = characterClass,
                        stat = stat,
                        weapon = weapon,
                        ability = ability,
                        name = name,
                        skinTone = skin,
                        eyeColor = eye,
                        hairStyle = hairStyle,
                        hairColor = hairColor,
                        earType = ear,
                        onStartOver = {
                            navController.popBackStack("home", inclusive = false)
                        }
                    )
                }
            }
        }
    }
}
