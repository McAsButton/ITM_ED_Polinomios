import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class Polinomio {
    private Nodo cabeza;

    public Polinomio() {
        cabeza = null;
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void agregar(Nodo n) {
        if (n != null) {
            if (cabeza == null) {
                cabeza = n;
            } else {
                Nodo apuntador = cabeza;
                Nodo predecesor = null;
                int encontrado = 0;

                while (apuntador != null && encontrado == 0) {
                    if (n.getExponente() == apuntador.getExponente()) {
                        encontrado = 1;
                    } else if (n.getExponente() < apuntador.getExponente()) {
                        encontrado = 2;
                    } else {
                        predecesor = apuntador;
                        apuntador = apuntador.siguiente;
                    }
                }
                if (encontrado == 1) {
                    double coeficiente = n.getCoeficiente() + apuntador.getCoeficiente();
                    if (coeficiente == 0) {
                        // Eliminar el nodo
                        if (predecesor == null) {
                            cabeza = apuntador.siguiente;
                        } else {
                            predecesor.siguiente = apuntador.siguiente;
                        }
                    } else {
                        apuntador.setCoeficiente(coeficiente);
                    }
                } else {
                    insertar(n, predecesor);
                }
            }
        }
    }

    public void insertar(Nodo n, Nodo predecesor) {
        if (n != null) {
            if (predecesor == null) {
                n.siguiente = cabeza;
                cabeza = n;
            } else {
                n.siguiente = predecesor.siguiente;
                predecesor.siguiente = n;
            }
        }
    }

    public void limpiar() {
        cabeza = null;
    }

    public String[] getTextos() {
        String[] lineas = new String[2];
        String espacio = " ";
        lineas[0] = "";
        lineas[1] = "";
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            String texto = String.valueOf(apuntador.getCoeficiente()) + "X";
            if (apuntador.getCoeficiente() >= 0) {
                texto = "+" + texto;
            }
            lineas[0] += String.format("%0" + texto.length() + "d", 0).replace("0", espacio);
            lineas[1] += texto;

            texto = String.valueOf(apuntador.getExponente());
            lineas[0] += texto;
            lineas[1] += String.format("%0" + texto.length() + "d", 0).replace("0", espacio);
            apuntador = apuntador.siguiente;
        }
        return lineas;
    }

    public void mostrar(JLabel lbl) {
        String[] lineas = getTextos();
        String espacio = "&nbsp;";
        lineas[0] = lineas[0].replace(" ", espacio);
        lineas[1] = lineas[1].replace(" ", espacio);
        lbl.setFont(new Font("Courier New", Font.PLAIN, 12));
        lbl.setText("<html>" + lineas[0] + "<br>" + lineas[1] + "</html>");
    }

    public Polinomio derivar() {
        Polinomio pR = new Polinomio();
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            double coeficiente = apuntador.getCoeficiente() * apuntador.getExponente();
            int exponente = apuntador.getExponente() - 1;
            if (exponente >= 0) {
                Nodo n = new Nodo(coeficiente, exponente);
                pR.agregar(n);
            }
            apuntador = apuntador.siguiente;
        }
        return pR;
    }

    public Polinomio derivarprof() {
        Polinomio pR = new Polinomio();
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            if (apuntador.getExponente() != 0) {
                Nodo n = new Nodo(apuntador.getCoeficiente() * apuntador.getExponente(), apuntador.getExponente() - 1);
                pR.agregar(n);
            }
            apuntador = apuntador.siguiente;
        }
        return pR;
    }

    // ****************** Metodos estaticos ******************

    public static Polinomio sumar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        Nodo apuntador2 = p2.getCabeza();

        while (apuntador1 != null || apuntador2 != null) {
            Nodo n = null;
            if (apuntador1 != null && apuntador2 != null && apuntador1.getExponente() == apuntador2.getExponente()) {
                if (apuntador1.getCoeficiente() + apuntador2.getCoeficiente() != 0) {
                    n = new Nodo(apuntador1.getCoeficiente() + apuntador2.getCoeficiente(), apuntador1.getExponente());
                }
                apuntador1 = apuntador1.siguiente;
                apuntador2 = apuntador2.siguiente;
            } else if ((apuntador2 == null)
                    || (apuntador1 != null && apuntador1.getExponente() < apuntador2.getExponente())) {
                n = new Nodo(apuntador1.getCoeficiente(), apuntador1.getExponente());
                apuntador1 = apuntador1.siguiente;
            } else if ((apuntador1 == null)
                    || (apuntador2 != null && apuntador1.getExponente() > apuntador2.getExponente())) {
                n = new Nodo(apuntador2.getCoeficiente(), apuntador2.getExponente());
                apuntador2 = apuntador2.siguiente;
            }
            if (n != null) {
                pR.agregar(n);
            }
        }
        return pR;
    }

    public static Polinomio restar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        Nodo apuntador2 = p2.getCabeza();

        while (apuntador1 != null || apuntador2 != null) {
            Nodo n = null;
            if (apuntador1 != null && apuntador2 != null && apuntador1.getExponente() == apuntador2.getExponente()) {
                if (apuntador1.getCoeficiente() - apuntador2.getCoeficiente() != 0) {
                    n = new Nodo(apuntador1.getCoeficiente() - apuntador2.getCoeficiente(), apuntador1.getExponente());
                }
                apuntador1 = apuntador1.siguiente;
                apuntador2 = apuntador2.siguiente;
            } else if ((apuntador2 == null)
                    || (apuntador1 != null && apuntador1.getExponente() < apuntador2.getExponente())) {
                n = new Nodo(apuntador1.getCoeficiente(), apuntador1.getExponente());
                apuntador1 = apuntador1.siguiente;
            } else if ((apuntador1 == null)
                    || (apuntador2 != null && apuntador1.getExponente() > apuntador2.getExponente())) {
                n = new Nodo(-apuntador2.getCoeficiente(), apuntador2.getExponente());
                apuntador2 = apuntador2.siguiente;
            }
            if (n != null) {
                pR.agregar(n);
            }
        }
        return pR;
    }

    public static Polinomio multiplicar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        Nodo apuntador2 = p2.getCabeza();

        while (apuntador1 != null) {
            while (apuntador2 != null) {
                double coeficiente = apuntador1.getCoeficiente() * apuntador2.getCoeficiente();
                int exponente = apuntador1.getExponente() + apuntador2.getExponente();
                Nodo n = new Nodo(coeficiente, exponente);
                pR.agregar(n);
                apuntador2 = apuntador2.siguiente;
            }
            apuntador1 = apuntador1.siguiente;
            apuntador2 = p2.getCabeza();
        }
        return pR;
    }

    public static List<Polinomio> dividir(Polinomio dividendo, Polinomio divisor) {
        Polinomio cociente = new Polinomio();
        Polinomio residuo = new Polinomio();

        residuo = dividendo;

        Nodo ultimoDividendo = residuo.getUltimoNodo(); // Obtener el último nodo de p1
        Nodo ultimoDivisor = divisor.getUltimoNodo(); // Obtener el último nodo de p2

        while (ultimoDividendo != null && ultimoDivisor != null) {
            // Verificar que la división es posible
            if (ultimoDividendo.getExponente() < ultimoDivisor.getExponente()) {
                break;
            }
    
            // Dividir coeficientes y calcular el nuevo término del cociente
            double coeficiente = ultimoDividendo.getCoeficiente() / ultimoDivisor.getCoeficiente();
            int exponente = ultimoDividendo.getExponente() - ultimoDivisor.getExponente();
            Nodo nuevoTermino = new Nodo(coeficiente, exponente);
            cociente.agregar(nuevoTermino);
    
            // Multiplicar el divisor por el nuevo término y restar del residuo
            Polinomio termino = new Polinomio();
            Nodo nodoTermino = new Nodo(coeficiente, exponente);
            termino.agregar(nodoTermino);
            Polinomio producto = multiplicar(termino, divisor);
            residuo = restar(residuo, producto);
    
            // Actualizar el último nodo del residuo para la siguiente iteración
            ultimoDividendo = residuo.getUltimoNodo();
        }

        List<Polinomio> resultado = new ArrayList<>();
        resultado.add(cociente);
        resultado.add(residuo);
        return resultado;
    }

    public Nodo getUltimoNodo() {
        Nodo actual = cabeza;
        if (actual == null)
            return null;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        return actual;
    }
}
