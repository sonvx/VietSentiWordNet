package vnu.uet.vietsentiwordnet.dictionary;

public class Synset{
	public int line;
    public String term;
    double NegScore;
    double PosScore;
    Synset(){
        NegScore = 0;
        PosScore = 0;
        term = null;
    }
    public Synset(String _term,double _Neg,double _Pos, int line_){
        NegScore = _Neg;
        PosScore = _Pos;
        term = _term;
        line = line_;
    }
    public double getScore(){
        return PosScore-NegScore;
    }
}