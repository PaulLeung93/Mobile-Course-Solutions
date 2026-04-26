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

                composable("home") {
                    HomeScreen(
                        onBeginClicked = { navController.navigate("face") }
                    )
                }

                composable("face") {
                    FaceScreen(
                        onFaceSelected = { face ->
                            navController.navigate(
                                "class/${face.skinTone}/${face.eyeColor}/${face.hairStyle}/${face.hairColor}/${face.earType}"
                            )
                        }
                    )
                }

                composable("class/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val skin      = back.arguments?.getString("skin")      ?: ""
                    val eye       = back.arguments?.getString("eye")       ?: ""
                    val hairStyle = back.arguments?.getString("hairStyle") ?: ""
                    val hairColor = back.arguments?.getString("hairColor") ?: ""
                    val ear       = back.arguments?.getString("ear")       ?: ""
                    ClassScreen(
                        onClassSelected = { characterClass ->
                            navController.navigate("weapon/$characterClass/$skin/$eye/$hairStyle/$hairColor/$ear")
                        }
                    )
                }

                composable("weapon/{characterClass}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val characterClass = back.arguments?.getString("characterClass") ?: ""
                    val skin           = back.arguments?.getString("skin")           ?: ""
                    val eye            = back.arguments?.getString("eye")            ?: ""
                    val hairStyle      = back.arguments?.getString("hairStyle")      ?: ""
                    val hairColor      = back.arguments?.getString("hairColor")      ?: ""
                    val ear            = back.arguments?.getString("ear")            ?: ""
                    WeaponScreen(
                        characterClass = characterClass,
                        onWeaponSelected = { weapon ->
                            navController.navigate("stat/$characterClass/$weapon/$skin/$eye/$hairStyle/$hairColor/$ear")
                        }
                    )
                }

                composable("stat/{characterClass}/{weapon}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val characterClass = back.arguments?.getString("characterClass") ?: ""
                    val weapon         = back.arguments?.getString("weapon")         ?: ""
                    val skin           = back.arguments?.getString("skin")           ?: ""
                    val eye            = back.arguments?.getString("eye")            ?: ""
                    val hairStyle      = back.arguments?.getString("hairStyle")      ?: ""
                    val hairColor      = back.arguments?.getString("hairColor")      ?: ""
                    val ear            = back.arguments?.getString("ear")            ?: ""
                    StatScreen(
                        characterClass = characterClass,
                        weapon = weapon,
                        onStatSelected = { stat ->
                            navController.navigate("ability/$characterClass/$stat/$weapon/$skin/$eye/$hairStyle/$hairColor/$ear")
                        }
                    )
                }

                composable("ability/{characterClass}/{stat}/{weapon}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val characterClass = back.arguments?.getString("characterClass") ?: ""
                    val stat           = back.arguments?.getString("stat")           ?: ""
                    val weapon         = back.arguments?.getString("weapon")         ?: ""
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
                            navController.navigate("card/$characterClass/$stat/$weapon/$ability/$skin/$eye/$hairStyle/$hairColor/$ear")
                        }
                    )
                }

                composable("card/{characterClass}/{stat}/{weapon}/{ability}/{skin}/{eye}/{hairStyle}/{hairColor}/{ear}") { back ->
                    val characterClass = back.arguments?.getString("characterClass") ?: ""
                    val stat           = back.arguments?.getString("stat")           ?: ""
                    val weapon         = back.arguments?.getString("weapon")         ?: ""
                    val ability        = back.arguments?.getString("ability")        ?: ""
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
