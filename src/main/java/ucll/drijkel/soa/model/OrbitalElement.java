package ucll.drijkel.soa.model;

public class OrbitalElement
{
    private int id;
    private String object;
    private int epoch;
    private double tp;
    private double e;
    private double i;
    private double w;
    private double node;
    private double q;
    private double ql;
    private double p;
    private double moid;
    private double a1;
    private double a2;
    private double a3;
    private double dt;
    private String ref;
    private String object_name;

    public OrbitalElement() {}

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", Object:" + object +
                ", Epoch:" + epoch +
                ", TP:" + tp +
                ", e:" + e +
                ", I:" + i +
                ", w:" + w +
                ", Node:" + node +
                ", q:" + q +
                ", Ql:" + ql +
                ", P:" + p +
                ", MOID:" + moid +
                ", A1:" + a1 +
                ", A2:" + a2 +
                ", A3:" + a3 +
                ", DT:" + dt +
                ", ref:" + ref +
                ", Object_name:" + object_name +
                "}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public double getTp() {
        return tp;
    }

    public void setTp(double tp) {
        this.tp = tp;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getNode() {
        return node;
    }

    public void setNode(double node) {
        this.node = node;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public double getQl() {
        return ql;
    }

    public void setQl(double ql) {
        this.ql = ql;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getMoid() {
        return moid;
    }

    public void setMoid(double moid) {
        this.moid = moid;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public double getA3() {
        return a3;
    }

    public void setA3(double a3) {
        this.a3 = a3;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }
}
