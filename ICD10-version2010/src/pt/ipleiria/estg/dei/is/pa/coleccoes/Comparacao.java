package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface Comparacao<T>{
  /* Devolve
     > 0  se a ordem do 1º elemento for superior à do 2º,
       0  se a ordem do 1º elemento for igual à do 2º e
     < 0  se a ordem do 1º elemento for inferior à do 2º.
  */
  int comparar(T o1, T o2);
}

