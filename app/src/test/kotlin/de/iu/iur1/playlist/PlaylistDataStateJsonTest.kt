package de.iu.iur1.playlist

import org.junit.Test

class PlaylistDataTest {

    @Test
    fun jsonFromString() {
        val testJson = "{\n" +
                "  \"rating\": 5,\n" +
                "  \"entries\": [\n" +
                "    {\n" +
                "      \"title\": \"FEARLESS\",\n" +
                "      \"time\": \"17:30\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Blue Flame\",\n" +
                "      \"time\": \"17:27\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        val result = PlaylistData.fromString(testJson)
        println(result?.toString())
        assert(result != null)
        assert(result?.rating == 5)
        assert(result?.entries?.size == 2)
        assert(result?.entries?.get(0)?.title == "FEARLESS")
        assert(result?.entries?.get(0)?.time == "17:30")
        assert(result?.entries?.get(1)?.title == "Blue Flame")
        assert(result?.entries?.get(1)?.time == "17:27")
    }
}