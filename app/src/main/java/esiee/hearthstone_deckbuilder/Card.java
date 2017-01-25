package esiee.hearthstone_deckbuilder;

import android.os.Parcel;
import android.os.Parcelable;

public class Card {
    private String          id;

    private String          dbfId;
    private String          name;
    private String          text;

    private int             cost;
    private int             attack;
    private int             health;
    private String[]        mechanics;

    private String          rarity;
    private String          type;
    private int[]           dust;

    private String          set;
    private String          faction;
    private String          artist;
    private String          flavor;
    public String getDbfId() {
        return dbfId;
    }

    public void setDbfId(String dbfId) {
        this.dbfId = dbfId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String[] getMechanics() {
        return mechanics;
    }

    public void setMechanics(String[] mechanics) {
        this.mechanics = mechanics;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int[] getDust() {
        return dust;
    }

    public void setDust(int[] dust) {
        this.dust = dust;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}
