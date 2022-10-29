package fr.benco11.asynconf.utils;

public class MathUtils {
    /**
     * Arrondis un double
     *
     * @param value     double à arrondir
     * @param precision précision de l'arrondi
     * @return double arrondi
     */
    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value*scale)/scale;
    }

    public static double daysToH(double days) {
        return days*24;
    }

    /**
     * Arrondis un double et le formate (enlève les 0 lorsque le résultat est rond et ne garde que le bon nombre de chiffres après la virgule)
     *
     * @param value     double à traiter
     * @param precision précision de l'arrondi
     * @return String avec le double formaté
     */
    public static String roundAndFormat(double value, int precision) {
        double rounded = round(value, precision);
        precision = (rounded == Math.floor(rounded)) ? 0 : precision;
        return String.format("%." + precision + "f", rounded);
    }

    private MathUtils() {
    }
}
