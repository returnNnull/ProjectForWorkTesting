package com.example.projectforworktesting.mock.data.room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportFactData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val tittle: String = "",
    val about: String = "",
    val img: String = "",
    val like: Boolean = false
) {

    companion object {

        fun getExampleData(): List<SportFactData> {
            return listOf(
                SportFactData(
                    1,
                    "Who participated in all football championships?",
                    "Brazil is the only country in the world that has taken part in all the World Cups.",
                    "https://grodnoinvest.by/uploads/news/2023/february/brazil-fez-outlook.jpg"
                ),
                SportFactData(
                    2,
                    "Sport in Cinema",
                    "The most popular sport in cinema is boxing.",
                    "https://ss.sport-express.ru/userfiles/materials/162/1629640/volga.jpg"
                ),
                SportFactData(
                    3,
                    "Sportsmen nudists",
                    "In ancient Greece, the birthplace of the Olympic Games, all participants set their records without clothes, naked.",
                    "https://im.kommersant.ru/Issues.photo/MONEY/2016/030/KMO_088734_01747_1_t218_212748.jpg"
                ),
                SportFactData(4,
                "Fact4",
                "About4",
                "https://4brain.ru/blog/wp-content/uploads/2020/08/top-7-prichin-po-kotorym-sport-polezen-dlja-mozga.png"),
                SportFactData(5,
                    "Fact5",
                    "About5",
                    "https://4brain.ru/blog/wp-content/uploads/2020/08/top-7-prichin-po-kotorym-sport-polezen-dlja-mozga.png"),
                SportFactData(6,
                    "Fact6",
                    "About6",
                    "https://4brain.ru/blog/wp-content/uploads/2020/08/top-7-prichin-po-kotorym-sport-polezen-dlja-mozga.png"),
                SportFactData(7,
                    "Fact7",
                    "About7",
                    "https://4brain.ru/blog/wp-content/uploads/2020/08/top-7-prichin-po-kotorym-sport-polezen-dlja-mozga.png")
            )
        }
    }

}