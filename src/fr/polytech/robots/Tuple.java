package fr.polytech.robots;

/**
 * Classe représentant un objet à deux entiers : x et y.
 */
public class Tuple {

    private int x;
    private int y;

    /**
     * Constructeur.
     *
     * @param x Valeur de X.
     * @param y Valeur de Y.
     */
    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return la valeur de X.
     */
    public int getX() {
        return x;
    }

    /**
     * @return la valeur de Y.
     */
    public int getY() {
        return y;
    }

    /**
     * @return le hashcode unique de l'objet.
     */
    @Override
    public int hashCode() {
        return Integer.parseInt(Integer.toString(x) + Integer.toString(y));
    }

    /**
     * Compare un objet au tuple et décide s'ils sont égaux.
     * @param obj Objet à comparer.
     * @return vrai s'ils sont égaux, faux sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tuple))
            return false;

        Tuple tuple = (Tuple) obj;

        return tuple.getX() == x && tuple.getY() == y;
    }

    /**
     * Convertit l'objet en String.
     *
     * La String de retour sera de format `(x;y)` avec pour x et y leurs valeurs respectives.
     *
     * @return une représentation en String de l'objet.
     */
    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}