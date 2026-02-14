import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class Mapa {
    private char[][] grade;

    public Mapa() {
        this.grade = new char[13][13];
    }

    public void carregarMapa(String nomeArquivo) throws Exception {
        File arquivo = new File(nomeArquivo);
        Scanner leitor = new Scanner(arquivo);
        
        for (int l = 0; l < 13; l++) {
            String linha = "";
            if (leitor.hasNextLine()) {
                linha = leitor.nextLine();
            }

            for (int c = 0; c < 13; c++) {
                //pega o caractere ou usa '_' se a linha for curta
                char caractere = (c < linha.length()) ? linha.charAt(c) : '_';
                
                //converte espaços ' ' em chão '_'
                if (caractere == ' ') caractere = '_';
                
                grade[l][c] = caractere;
            }
        }
        leitor.close();
    }

    public void posicionarElementos() {
        Random random = new Random();
        
        //inimigo (I)
        boolean posicionadoI = false;
        int tentativas = 0;
        while (!posicionadoI && tentativas < 100) {
            int c = random.nextInt(13);
            if (grade[0][c] == '_') {
                grade[0][c] = 'I';
                posicionadoI = true;
            }
            tentativas++;
        }
        if (!posicionadoI) grade[0][6] = 'I'; 

        //jogador (T)
        boolean posicionadoT = false;
        tentativas = 0;
        while (!posicionadoT && tentativas < 100) {
            int c = random.nextInt(13);
            if (grade[12][c] == '_' && grade[12][c] != 'I') {
                grade[12][c] = 'T';
                posicionadoT = true;
            }
            tentativas++;
        }
        if (!posicionadoT) grade[12][6] = 'T';
    }

    public char[][] getGrade() {
        return grade;
    }

    public char getElemento(int l, int c) {
        if(l >= 0 && l < 13 && c >= 0 && c < 13)
            return grade[l][c];
        return '#';
    }
    
    //correção visual (margens do mapa)
    public void imprimir() {
        System.out.println("---------------------------------------"); 
        
        for (int l = 0; l < 13; l++) {
            for (int c = 0; c < 13; c++) {
                System.out.print(grade[l][c] + "  "); 
            }
            System.out.println();
        }
        System.out.println("---------------------------------------");
    }
}