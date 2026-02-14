public class Tiro {
    int posicaoX, posicaoY;
    int velocidade;
    char posicao;
    char[][] mapa;
    
    //referências para acessar a vida dos tanques
    private Tanque jogador; 
    private Inimigo inimigo; 
    
    //construtor
    Tiro(int posicaoX, int posicaoY, char posicao, int velocidade, char[][] mapa, Tanque jogador, Inimigo inimigo){
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.posicao = posicao;
        this.velocidade = velocidade;
        this.mapa = mapa;
        this.jogador = jogador;
        this.inimigo = inimigo;
    }
    
    //retorna TRUE se der Game Over, FALSE se o jogo continua
    boolean atirar(){
        int x = posicaoX;
        int y = posicaoY;
        boolean gameOver = false;
        
        while (true) {
            
            //atualiza posição
            if (posicao == 'w') x--;
            else if (posicao == 'a') y--;
            else if (posicao == 's') x++;
            else if (posicao == 'd') y++;

            //verifica bordas
            if (x < 0 || x >= mapa.length || y < 0 || y >= mapa[0].length)
                break; //sai do mapa

            char c = mapa[x][y];

            //colisões
            //parede de Aço
            if (c == '#') {
                break; //bala some
            }

            //tijolo
            if (c == '%') {
                mapa[x][y] = '_'; //remove o tijolo imediatamente
                break;
            }

            //inimigo
            if (c == 'I') {
                if (inimigo != null) {
                    inimigo.receberDano();
                    System.out.println(">> ACERTOU O INIMIGO! Vida: " + inimigo.getVida());
                    if (inimigo.getVida() <= 0) {
                        mapa[x][y] = '_'; // Inimigo morre
                        System.out.println(">> INIMIGO DESTRUIDO!");
                    }
                }
                break;
            }

            //jogador (Se o inimigo atirar nele)
            if (c == 'T') {
                if (jogador != null) {
                    jogador.receberDano();
                    System.out.println(">> VOCE LEVOU UM TIRO!");
                    if (jogador.getVida() <= 0) {
                        mapa[x][y] = 'X';
                        System.out.println(">>> GAME OVER: SEU TANQUE EXPLODIU! <<<");
                        gameOver = true;
                    }
                }
                break;
            }

            //base
            if (c == 'B') {
                mapa[x][y] = 'X';
                System.out.println(">>> GAME OVER: A BASE FOI DESTRUIDA! <<<");
                gameOver = true;
                break;
            }
            
            //animação
            if (!gameOver) {
                char anterior = mapa[x][y]; //salva o que estava lá (geralmente '_')
                
                mapa[x][y] = '*'; //desenha bala
                imprimirMapa();
                mapa[x][y] = '_'; //apaga bala (restaura chão)
                
                //caso não seja o chão, restaura o anterior
                if (anterior != '_') mapa[x][y] = anterior; 

                try {
                    Thread.sleep(velocidade);
                } catch (InterruptedException e) {}
            }
        }
        return gameOver;
    }

    void imprimirMapa(){
        //truque de limpeza do mapa
        for(int i = 0; i < 50; i++) {
            System.out.println();
        }

        System.out.println("---------------------------");
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------");
    }
}