package edu.uph.m24si1.uas_signlanguageapp;

public class QuizItem {
    public int idGambarWebP;
    public String opsiA;
    public String opsiB;
    public String opsiC;
    public String opsiD;
    public String jawabanBenar;

    public QuizItem(int idGambarWebP, String opsiA, String opsiB, String opsiC, String opsiD, String jawabanBenar) {
        this.idGambarWebP = idGambarWebP;
        this.opsiA = opsiA;
        this.opsiB = opsiB;
        this.opsiC = opsiC;
        this.opsiD = opsiD;
        this.jawabanBenar = jawabanBenar;
    }
}