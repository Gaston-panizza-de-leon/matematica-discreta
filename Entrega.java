import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * Cada tema té el mateix pes, i l'avaluació consistirà en:
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - La neteja del codi (pensau-ho com faltes d'ortografia). L'estàndar que heu de seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . No és
 *   necessari seguir-la estrictament, però ens basarem en ella per jutjar si qualcuna se'n desvia
 *   molt.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Lucas Gastón Panizza De León
 * - Nom 2: Andreu Valerià
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital abans de la data que se us hagui
 * comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més fàcilment
 * les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat, assegurau-vos
 * que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
    /*
     * Aquí teniu els exercicis del Tema 1 (Lògica).
     *
     * Els mètodes reben de paràmetre l'univers (representat com un array) i els
     * predicats adients
     * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és
     * un element de
     * l'univers, podeu fer-ho com `p.test(x)`, té com resultat un booleà. Els
     * predicats de dues
     * variables són de tipus `BiPredicate<Integer, Integer>` i similarment
     * s'avaluen com
     * `p.test(x, y)`.
     *
     * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats
     * retorneu `true`
     * o `false` segons si la proposició donada és certa (suposau que l'univers és
     * suficientment
     * petit com per utilitzar la força bruta)
     */
    static class Tema1 {
        /*
         * És cert que ∀x,y. P(x,y) -> Q(x) ^ R(y) ?
         */
        static boolean exercici1(
                int[] universe,
                BiPredicate<Integer, Integer> p,
                Predicate<Integer> q,
                Predicate<Integer> r) {
            for (int i = 0; i < universe.length; i++) {
                for (int j = 0; j < universe.length; j++) {
                    // Recorremos todo el universo.
                    boolean a = p.test(universe[i], universe[j]);
                    boolean b = q.test(universe[i]) && r.test(universe[j]);
                    if (a && !b) {
                        // En el caso de que no se cumpla la implicación.
                        return false;
                    }
                }
            }
            return true; // TO DO
        }

        /*
         * És cert que ∃!x. ∀y. Q(y) -> P(x) ?
         */
        static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
            int count = 0;
            int x = 0;
            for (int i = 0; i < universe.length; i++) {
                for (int j = 0; j < universe.length; j++) {
                    // Recorremos todo el universo.
                    if (!(q.test(universe[j]) && !p.test(universe[i]))) {
                        // En el caso de que se cumpla la implicación:
                        if (count == 0) {
                            // Si es la primera vez, se guarda la posición del elemento comprobado.
                            x = i;
                            count++;
                        }
                        if (x != i) {
                            // Si es con otro elemento se sigue sumando.
                            count++;
                        }
                    }
                }
            }
            // Devuelve si el numero de casos que se cumplen es igual a 1.
            return count == 1; // TO DO
        }

        /*
         * És cert que ¬(∃x. ∀y. y ⊆ x) ?
         *
         * Observau que els membres de l'univers són arrays, tractau-los com conjunts i
         * podeu suposar
         * que cada un d'ells està ordenat de menor a major.
         */
        static boolean exercici3(int[][] universe) {
            int[] x;
            int[] y;
            boolean contiene;
            for (int i = 0; i < universe.length; i++) {
                contiene = true;
                x = universe[i];
                for (int j = 0; j < universe.length; j++) { 
                    // Recorremos todo el universo.
                    y = universe[j];
                    if (!contiene(x, y)) { 
                        //Si no contiene alguno de los valores sale del for.
                        contiene = false;
                        break;
                    }
                }
                if (contiene) {
                    //En el caso de que un conjunto contenga todos.
                    return false;
                }
            }
            return true; // TO DO
        }
        /***
         * Este metodo compara dos conjuntos y devuelve si uno contiene al otro.
         * @param x conjunto a contener al otro.
         * @param y conjunto que tiene que estar contenido.
         */
        static boolean contiene(int[] x, int[] y) {
            boolean contiene;
            if (y.length > x.length) {
                return false;
            }
            for (int i = 0; i < y.length; i++) {
                contiene = false;
                for (int j = 0; j < x.length; j++) {
                    if (y[i] == x[j]) {
                        contiene = true;
                    }
                }
                if (!contiene) {
                    return false;
                }
            }
            return true;
        }

        /*
         * És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?
         */
        static boolean exercici4(int[] universe, int n) {
            int x; 
            int y;
            int count = 0;
            for (int i = 0; i < universe.length; i++) {
                for (int j = 0; j < universe.length; j++) {
                    // Recorremos todo el universo.
                    x = universe[i];
                    y = universe[j];
                    if ((x * y) % n == 1) {
                        //Si se comple la condición:
                        if (count == 0) {
                            //Y es la primera vez, suma una vez y guarda la posición.
                            y = i;
                            count++;
                        }
                        if (y != i) {
                            //Si es diferente al valor guardado:
                            return false;
                        }
                    }
                }
                if (count != 1) {
                    //Si la cantidad de soluciones es diferente a 1:
                    return false;
                }
                count = 0;
            }
            return true; // TO DO
        }

        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
         * `main`)
         */
        static void tests() {
            // Exercici 1
            // ∀x,y. P(x,y) -> Q(x) ^ R(y)

            assertThat(
                    exercici1(
                            new int[] { 2, 3, 5, 6 },
                            (x, y) -> x * y <= 4,
                            x -> x <= 3,
                            x -> x <= 3));

            assertThat(
                    !exercici1(
                            new int[] { -2, -1, 0, 1, 2, 3 },
                            (x, y) -> x * y >= 0,
                            x -> x >= 0,
                            x -> x >= 0));

            // Exercici 2
            // ∃!x. ∀y. Q(y) -> P(x) ?

            assertThat(
                    exercici2(
                            new int[] { -1, 1, 2, 3, 4 },
                            x -> x < 0,
                            x -> true));

            assertThat(
                    !exercici2(
                            new int[] { 1, 2, 3, 4, 5, 6 },
                            x -> x % 2 == 0, // x és múltiple de 2
                            x -> x % 4 == 0 // x és múltiple de 4
                    ));

            // Exercici 3
            // ¬(∃x. ∀y. y ⊆ x) ?

            assertThat(
                    exercici3(new int[][] { { 1, 2 }, { 0, 3 }, { 1, 2, 3 }, {} }));

            assertThat(
                    !exercici3(new int[][] { { 1, 2 }, { 0, 3 }, { 1, 2, 3 }, {}, { 0, 1, 2, 3 } }));

            // Exercici 4
            // És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?

            assertThat(
                    exercici4(
                            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                            11));

            assertThat(
                    !exercici4(
                            new int[] { 0, 5, 7 },
                            13));
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 2 (Conjunts).
     *
     * De la mateixa manera que al Tema 1, per senzillesa tractarem els conjunts com
     * arrays (sense
     * elements repetits). Per tant, un conjunt de conjunts d'enters tendrà tipus
     * int[][].
     *
     * Les relacions també les representarem com arrays de dues dimensions, on la
     * segona dimensió
     * només té dos elements. Per exemple
     * int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
     * i també donarem el conjunt on està definida, per exemple
     * int[] a = {0,1,2};
     *
     * Les funcions f : A -> B (on A i B son subconjunts dels enters) les
     * representam donant int[] a,
     * int[] b, i un objecte de tipus Function<Integer, Integer> que podeu avaluar
     * com f.apply(x) (on
     * x és un enter d'a i el resultat f.apply(x) és un enter de b).
     */

    static class Tema2 {
        /*
         * És `p` una partició d'`a`?
         *
         * `p` és un array de conjunts, haureu de comprovar que siguin elements d'`a`.
         * Podeu suposar que
         * tant `a` com cada un dels elements de `p` està ordenat de menor a major.
         */
        static boolean exercici1(int[] a, int[][] p) {
            int[] bool = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                for (int[] j : p) {
                    for (int k = 0; k < j.length; k++) {
                        if (a[i] == j[k]) {
                            bool[i] += 1;
                        }
                    }
                }
            }
            for (int i = 0; i < bool.length; i++) {
                if (bool[i] != 1) {
                    return false;
                }
            }
            return true;
        }

        /*
         * Comprovau si la relació `rel` definida sobre `a` és un ordre parcial i que
         * `x` n'és el mínim.
         *
         * Podeu soposar que `x` pertany a `a` i que `a` està ordenat de menor a major.
         */
        static boolean exercici2(int[] a, int[][] rel, int x) {
            boolean reflexive = false;
            boolean antisimetric = false;
            boolean transitive = false;
            boolean Xminimo = false;
            int counter = 0;

            // Comprovar si la relación es reflexiva
            for (int i = 0; i < rel.length; i++) {
                if (rel[i][0] == rel[i][1]) {
                    counter++;
                }
            }
            if (counter == a.length) {
                reflexive = true;
            }

            counter = 0;
            // Comprovar si la relación es antisimétrica
            for (int i = 0; i < rel.length; i++) {
                for (int j = 0; j < rel.length; j++) {
                    if (rel[i][0] == rel[j][1] && rel[i][1] == rel[j][0] && rel[i][0] == rel[j][0]
                            && rel[i][1] == rel[j][1]) {
                        counter++;
                    }
                }
            }
            if (counter == a.length) {
                antisimetric = true;
            }

            counter = 0;
            // Comprovar si la relación es transitiva
            for (int i = 0; i < rel.length; i++) {
                for (int j = 0; j < rel.length; j++) {
                    for (int k = 0; k < rel.length; k++) {
                        if (rel[i][1] == rel[j][0] && rel[j][1] == rel[k][0] && rel[i][0] == rel[k][1]) {
                            counter++;
                        }
                    }
                }
            }

            if (counter == a.length) {
                transitive = true;
            }

            // Comprobar si x es el mínimo de la relación
            int minimals = 0;
            int minimumRel = 0;
            int minimum = 0;
            for (int i = 0; i < a.length; i++) {
                int relcounter = 0;
                for (int j = 0; j < rel.length; j++) {
                    if (rel[j][0] == a[i]) {
                        relcounter++;
                    }
                }
                if (minimumRel == 0 || relcounter < minimumRel) {
                    minimumRel = relcounter;
                    minimals = 0;
                    minimum = a[i];
                } else if (relcounter == minimumRel) {
                    minimals++;
                }
            }

            if (minimum == x) {
                Xminimo = true;
            }

            if (reflexive && antisimetric && transitive && Xminimo) {
                return true;
            }
            return false;
        }

        /*
         * Suposau que `f` és una funció amb domini `dom` i codomini `codom`. Trobau
         * l'antiimatge de
         * `y` (ordenau el resultat de menor a major, podeu utilitzar `Arrays.sort()`).
         * Podeu suposar
         * que `y` pertany a `codom` i que tant `dom` com `codom` també estàn ordenats
         * de menor a major.
         */
        static int[] exercici3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
            ArrayList<Integer> antiimagenes = new ArrayList<>();
            for (int i = 0; i < dom.length; i++) {
                if (f.apply(dom[i]) == y) {
                    antiimagenes.add(dom[i]);
                }
            }

            int[] arr = new int[antiimagenes.size()];

            // ArrayList to Array Conversion
            for (int i = 0; i < antiimagenes.size(); i++)
                arr[i] = antiimagenes.get(i);

            return arr;
        }

        /*
         * Suposau que `f` és una funció amb domini `dom` i codomini `codom`. Retornau:
         * - 3 si `f` és bijectiva
         * - 2 si `f` només és exhaustiva
         * - 1 si `f` només és injectiva
         * - 0 en qualsevol altre cas
         *
         * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major. Per
         * comoditat, podeu
         * utilitzar les constants definides a continuació:
         */
        static final int NOTHING_SPECIAL = 0;
        static final int INJECTIVE = 1;
        static final int SURJECTIVE = 2;
        static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

        static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
            boolean injective;
            int tipo1 = 1;
            int[] function = new int[dom.length];
            if (dom.length < codom.length) {
                for (int i = 0; i < dom.length; i++) {
                    function[i] = f.apply(dom[i]);
                }
                int checkrepeat = 0;
                for (int i : function) {
                    injective = false;
                    for (int x : codom) {
                        if (i == x) {
                            injective = true;
                            checkrepeat++;
                            break;
                        }
                    }
                    if (injective == false) {
                        tipo1 = 0;
                        break;
                    }
                }
                if (tipo1 == 1 && checkrepeat == dom.length) {
                    return INJECTIVE;
                } else {
                    return NOTHING_SPECIAL;
                }

            } else if (dom.length > codom.length) {
                for (int i : dom) {
                    injective = false;
                    for (int x : codom) {
                        if (f.apply(i) == x) {
                            injective = true;
                            break;
                        }
                    }
                    if (injective == false) {
                        tipo1 = 0;
                        break;
                    }
                }
                if (tipo1 == 1) {
                    return SURJECTIVE;
                } else {
                    return NOTHING_SPECIAL;
                }
            } else {
                for (int i = 0; i < dom.length; i++) {
                    function[i] = f.apply(dom[i]);
                }
                Arrays.sort(function);
                if (Arrays.equals(function, codom)) {
                    return BIJECTIVE;
                } else {
                    return NOTHING_SPECIAL;
                }
            }
        }

        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
         * `main`)
         */
        static void tests() {
            // Exercici 1
            // `p` és una partició d'`a`?

            assertThat(
                    exercici1(
                            new int[] { 1, 2, 3, 4, 5 },
                            new int[][] { { 1, 2 }, { 3, 5 }, { 4 } }));

            assertThat(
                    !exercici1(
                            new int[] { 1, 2, 3, 4, 5 },
                            new int[][] { { 1, 2 }, { 5 }, { 1, 4 } }));

            // Exercici 2
            // és `rel` definida sobre `a` d'ordre parcial i `x` n'és el mínim?

            ArrayList<int[]> divisibility = new ArrayList<int[]>();

            for (int i = 1; i < 8; i++) {
                for (int j = 1; j <= i; j++) {
                    if (i % j == 0) {
                        // i és múltiple de j, és a dir, j|i
                        divisibility.add(new int[] { i, j });
                    }
                }
            }

            assertThat(
                    exercici2(
                            new int[] { 1, 2, 3, 4, 5, 6, 7 },
                            divisibility.toArray(new int[][] {}),
                            1));

            assertThat(
                    !exercici2(
                            new int[] { 1, 2, 3 },
                            new int[][] { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 1, 2 }, { 2, 3 } },
                            1));

            assertThat(
                    !exercici2(
                            new int[] { 1, 2, 3, 4, 5, 6, 7 },
                            divisibility.toArray(new int[][] {}),
                            2));

            // Exercici 3
            // calcular l'antiimatge de `y`

            assertThat(
                    Arrays.equals(
                            new int[] { 0, 2 },
                            exercici3(
                                    new int[] { 0, 1, 2, 3 },
                                    new int[] { 0, 1 },
                                    x -> x % 2, // residu de dividir entre 2
                                    0)));

            assertThat(
                    Arrays.equals(
                            new int[] {},
                            exercici3(
                                    new int[] { 0, 1, 2, 3 },
                                    new int[] { 0, 1, 2, 3, 4 },
                                    x -> x + 1,
                                    0)));

            // Exercici 4
            // classificar la funció en res/injectiva/exhaustiva/bijectiva

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3 },
                            new int[] { 0, 1, 2, 3 },
                            x -> (x + 1) % 4) == BIJECTIVE);

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3 },
                            new int[] { 0, 1, 2, 3, 4 },
                            x -> x + 1) == INJECTIVE);

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3 },
                            new int[] { 0, 1 },
                            x -> x / 2) == SURJECTIVE);

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3 },
                            new int[] { 0, 1, 2, 3 },
                            x -> x <= 1 ? x + 1 : x - 1) == NOTHING_SPECIAL);
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 3 (Aritmètica).
     *
     */
    static class Tema3 {
        /*
         * Donat `a`, `b` retornau el màxim comú divisor entre `a` i `b`.
         *
         * Podeu suposar que `a` i `b` són positius.
         */
        static int exercici1(int a, int b) {
            int rest;
            if (a > b) {
                rest = a % b;
                if (rest == 0) {
                    //Hará recursion de él mismo hasta encontrar que el resto es 0.
                    return b;
                } else {
                    return exercici1(b, rest);
                }
            } else {
                rest = b % a;
                if (rest == 0) {
                    return a;
                } else {
                    return exercici1(a, rest);
                }
            }
            // TO DO
        }

        /*
         * Es cert que `a``x` + `b``y` = `c` té solució?.
         *
         * Podeu suposar que `a`, `b` i `c` són positius.
         */
        static boolean exercici2(int a, int b, int c) {
            //l'exercici 1 retorna el mcd de a y b.
            return exercici1(a, b) % c == 0;
        }

        /*
         * Quin es l'invers de `a` mòdul `n`?
         *
         * Retornau l'invers sempre entre 1 i `n-1`, en cas que no existeixi retornau -1
         */
        static int exercici3(int a, int n) {
            // calcula el inverso de a mod n
            boolean ExisteixInvers = false;
            int inverso = 0;
            for (int i = 1; i < n; i++) {
                if ((a * i) % n == 1) {
                    inverso = i;
                    ExisteixInvers = true;
                    break;
                }
            }

            if (ExisteixInvers) {
                return inverso;
            } else
                return -1;
        }

        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
         * `main`)
         */
        static void tests() {
            // Exercici 1
            // `mcd(a,b)`

            assertThat(
                    exercici1(2, 4) == 2);

            assertThat(
                    exercici1(1236, 984) == 12);

            // Exercici 2
            // `a``x` + `b``y` = `c` té solució?

            assertThat(
                    exercici2(4, 2, 2));
            assertThat(
                    !exercici2(5, 2, 2));
            // Exercici 3
            // invers de `a` mòdul `n`
            assertThat(exercici3(2, 5) == 3);
            assertThat(exercici3(2, 6) == -1);
        }
    }

    static class Tema4 {
        /*
         * Donada una matriu d'adjacencia `A` d'un graf no dirigit, retornau l'ordre i
         * la mida del graf.
         */
        static int[] exercici1(int[][] A) {
            // Dada una matriz de adyacencia A, devuelve el orden y el tamaño del grafo
            int orden = A.length;
            int mida = 0;
            for (int i = 0; i < orden; i++) {
                for (int j = 0; j < orden; j++) {
                    if (A[i][j] == 1 && A[j][i] == 1) {
                        mida++;
                    }
                }
            }
            mida = mida / 2;
            return new int[] { orden, mida };

        }

        /*
         * Donada una matriu d'adjacencia `A` d'un graf no dirigit, digau si el graf es
         * eulerià.
         */
        static boolean exercici2(int[][] A) {
            // dada una matriz de adyacencia A, devuelve si el grafo es euleriano
            boolean euleriano = true;
            int orden = A.length;
            int mida = 0;
            int[] grado = new int[orden];
            for (int i = 0; i < orden; i++) {
                for (int j = 0; j < orden; j++) {
                    if (A[i][j] == 1) {
                        grado[i]++;
                    }
                }
            }
            for (int i = 0; i < grado.length; i++) {
                if (grado[i] % 2 != 0) {
                    euleriano = false;
                }
            }
            return euleriano;
        }

        /*
         * Donat `n` el número de fulles d'un arbre arrelat i `d` el nombre de fills
         * dels nodes interiors,
         * retornau el nombre total de vèrtexos de l'arbre
         *
         */
        static int exercici3(int n, int d) {
            // dado n el numero de fulles de un arbol y d el numero de hijos de los nodos
            // interiores devuelve el numero total de vertices del arbol
            int total = 0;
            double niveli = 0;
            int i;
            for (i = 0; niveli <= n; i++) {
                total += (int) niveli;
                niveli = Math.pow(d, i);
            }
            if (Math.pow(d, i - 2) != n) {
                double fullesNiveli = Math.pow(d, i - 2) - 1;
                int nfullesSeg = 0;
                while ((nfullesSeg + fullesNiveli) < n) {
                    nfullesSeg += d;
                    if ((nfullesSeg + fullesNiveli) < n) {
                        fullesNiveli--;
                    }
                }
                total = total + nfullesSeg;
                return total;
            } else
                return total;
        }

        /*
         * Donada una matriu d'adjacencia `A` d'un graf connex no dirigit, digau si el
         * graf conté algún cicle.
         */
        

        static boolean exercici4(int[][] A) {
            Node nodos[] = new Node[A.length];
            int count = 0;
            for (int i = 0; i < A.length; i++) {
                //Recorremos la matriz adjunta.
                nodos[i] = new Node(A[i]); //Enlazamos los nodos que estan unidos.
                if (nodos[i].conected.size() > 1) {
                    count++;
                    nodos[i].posibleCiclo = true;
                }
            }
            if (count == A.length) {
                //Si todos los nodos tienen más de una conexión, será ciclo.
                return true;
            }
            for (int i = 0; i < A.length; i++) {
                if (nodos[i].posibleCiclo) {
                    //Si hay posibilidad de que lo sea, se comprueba.
                    if (esCiclo(nodos[i].code, nodos[i].code, nodos[i], nodos)) {
                        return true;
                    }
                }
            }
            return false; // TO DO
        }
        /**
         * Clase auxiliar para guardar los numeros a los que esta conectado un vértice.
         */
        private static class Node {
            public int code;
            public ArrayList<Integer> conected = new ArrayList();
            private static int incre = 0;
            public boolean posibleCiclo;

            public Node(int b[]) {
                code = incre;
                incre++;
                for (int i = 0; i < b.length; i++) {
                    if (b[i] == 1) {
                        conected.add(i);
                    }
                }
            }
        }
        /**
         * Método que comprueba si hay posibilidad de ciclo y si hay ciclo.
         * @param codeIni Nodo inicial.
         * @param codePre Nodo anterior al actual.
         * @param nodo Nodo actual.
         * @param nodos Lista de Todos los nodos.
         * @return
         */
        static boolean esCiclo(int codeIni, int codePre, Node nodo, Node[] nodos) {
            if (codeIni == nodo.code && codeIni != codePre) {
                return true;
            }
            for (int i = 0; i < nodo.conected.size(); i++) {
                if ((nodos[nodo.conected.get(i)].posibleCiclo) && nodo.conected.get(i) != codePre) {
                    if (esCiclo(codeIni, nodo.code, nodos[nodo.conected.get(i)], nodos))
                        return true;
                }
            }
            return false;
        }
        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
         * `main`)
         */
        static void tests() {
            // Exercici 1
            // `ordre i mida`

            assertThat(
                    Arrays.equals(exercici1(new int[][] { { 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 } }),
                            new int[] { 3, 2 }));

            assertThat(
                    Arrays.equals(
                            exercici1(new int[][] { { 0, 1, 0, 1 }, { 1, 0, 1, 1 }, { 0, 1, 0, 1 }, { 1, 1, 1, 0 } }),
                            new int[] { 4, 5 }));

            // Exercici 2
            // `Es eulerià?`

            assertThat(
                    exercici2(new int[][] { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 } }));
            assertThat(
                    !exercici2(new int[][] { { 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 } }));
            // Exercici 3
            // `Quants de nodes té l'arbre?`
            assertThat(exercici3(5, 2) == 9);
            assertThat(exercici3(7, 3) == 10);

            // Exercici 4
            // `Conté algún cicle?`
            assertThat(
                    exercici4(new int[][] { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 } }));
            assertThat(
                    !exercici4(new int[][] { { 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 } }));

        }
    }

    /*
     * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que
     * haurien de donar
     * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres
     * (no els tendrem en
     * compte, però és molt recomanable).
     *
     * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor
     * sigui `true`.
     */
    public static void main(String[] args) {
        Tema1.tests();
        Tema2.tests();
        Tema3.tests();
        Tema4.tests();
    }

    static void assertThat(boolean b) {
        if (!b)
            throw new AssertionError();
    }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
