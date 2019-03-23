package cn.yr.netty.callbacks;

/**
 * @Auther: linxinmin
 * @Date: 2018/11/15 09:31
 * @Description:
 */
public class Data {
    private int n;
    private int m;

    public Data(int n, int m) {
        this.n = n;
        this.m = m;
    }

    @Override
    public String toString() {
        return "Data{" +
                "n=" + n +
                ", m=" + m +
                '}';
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }
}
