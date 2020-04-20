package sovellus;

public class Toimintalogiikka {

    private double saldo;
    private double saldoIlmanKorkoa;
    private double kuukausiTallennus;
    private double vuosikorko;

    public Toimintalogiikka(double kuukausitallennus, double vuosikorko) {
        this.kuukausiTallennus = kuukausitallennus;
        this.vuosikorko = vuosikorko;
    }

    public double getKorko() {
        return this.vuosikorko;
    }

    public double kuukausitallennus() {
        return this.kuukausiTallennus;
    }

    public void eteneVuosi() {
        this.saldo += 12 * kuukausiTallennus;
        this.saldo += (double) saldo * (this.vuosikorko / 100);
        this.saldoIlmanKorkoa += 12 * kuukausiTallennus;
    }

    public double getVuodenKorko() {
        return saldo;
    }

    public double getSaldoIlmanKorkoa() {
        return this.saldoIlmanKorkoa;
    }

    public void setKorko(double korko) {
        this.saldo = 0;
        this.vuosikorko = korko;
        this.saldoIlmanKorkoa = 0;
    }

    public void setKuukausiTallennus(double kuukausiTallennus) {
        this.saldo = 0;
        this.kuukausiTallennus = kuukausiTallennus;
        this.saldoIlmanKorkoa = 0;
    }
}
