package fr.hegsis.otaliaclasse.profiles;

import fr.hegsis.otaliaclasse.classes.ClasseType;
import fr.hegsis.otaliaclasse.quests.Quest;

import java.util.Map;
import java.util.UUID;

public class Profile {
    private UUID uuid;
    private String userName;
    private Map<Quest, Integer> progression;
    private ClasseType classeType;
}
