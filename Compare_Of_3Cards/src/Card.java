/**
 * 扑克类
 */
public class Card {

    public Card(){

    }

    public Card(String suit, String num) {
        this.suit = suit;
        this.num = num;
    }

    private String suit;// 花色
    private String num; // 大小

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
