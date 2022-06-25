# ASD_5

Rozwiązanie każdego z zadań polega na:
1) zaproponowaniu możliwie najefektywniejszego czasowo rozwiązania
2) określeniu jego pesymistycznej złożoności czasowej

**Problem 1 - „Misja”**

Jest XXXIII wiek. Ludzie podróżują ultra-szybkimi statkami kosmicznymi pomiędzy zawieszonymi
w przestrzeni międzygwiezdnej ogromnymi hipermiastami. Kosmos jest w stanie wojny i dowolne
podróżowanie w przestrzeni kosmicznej jest zabronione. Do komunikacji służą tylko specjalnie
wybrane korytarze komunikacyjne łączące ze sobą hipermiasta. W tych korytarzach statki
kosmiczne poruszają się z prędkością światła, jednak odległości między hipermiastami są duże –
liczone zwykle w godzinach świetlnych. Stąd podróże trwają pewien czas. Niektóre hipermiasta są
jednak połączone specjalnymi tunelami czasoprzestrzennymi i dzięki temu podróż między nimi nic
nie trwa (!). Takich tuneli w całej przestrzeni jest jednak co najwyżej 50. Z racji tego, że trwa
kosmiczna wojna, to działania szpiegowskie są zakrojone na dużą skalę. Pewien szpieg znajduje się
w pierwszym hipermieście i ma za zadanie wykraść tajne informacje, które są ukryte w ostatnim z
hipermiast. W związku z tym musi przemieścić się on z hipermiasta nr 1 do hipermiasta nr n. Po
drodze może on swobodnie poruszać się zwykłymi korytarzami. Jednak podróżowanie tunelami
czasoprzestrzennymi jest niebezpieczne ze względu na ich niestabilność – szpieg może po prostu
zniknąć w wyższym wymiarze. Nie da się jednak ukryć, że tunele mogą pomóc w szybszym
pokonywaniu trasy. Szpieg stosuje w związku z tym zasadę ograniczania ryzyka i w czasie całej
podróży pozwala sobie na skorzystanie z co najwyżej k różnych tuneli czasoprzestrzennych (z
każdego po razie). Misja jest krytyczna i od jej powodzenia mogą zależeć losy wszechświata.
Szpieg musi ją wykonać w jak najkrótszym czasie. Czy pomożesz mu w wykonaniu zadania i
znalezienia najkrótszej czasowo trasy?

**Wejście:**
W pierwszej linii wejścia podana jest liczba n hipermiast, m korytarzy oraz maksymalną liczbę k
tuneli czasoprzestrzennych, których można użyć (1<n<=10000, 1<=m<=100000, 1<=k<=3). W
kolejnych m liniach są podane informacje dotyczące korytarzy: po 3 liczby całkowite oznaczające
odpowiednio numery połączonych miast oraz długość korytarza (co najwyżej 100 godzin
świetlnych).

**Wyjście:**
W pierwszej linii wyjście należy podać czas optymalnej trasy, a w kolejnej linii po kolei ciąg liczb
(numerów miast) opisujących trasę.

**Przykład:**

Wejście: 

5 7 1 

1 3 3 //między 1 i 3 korytarz powietrzny o długości 3 godzin świetl. 

2 4 2 //itd.

2 3 3

1 2 0 //tunel czasoprzestrzenny

3 4 0 //tunel czasoprzestrzenny

3 5 3

4 5 2


Wyjście:

4

1 2 4 5
