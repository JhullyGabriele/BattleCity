import java.util.Scanner;
import java.util.Random;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        Mapa mapa = new Mapa();
        Random sorteio = new Random();
        
        int pontuacao = 0; //variável para guardar os pontos
        boolean rodando = true;

        while (rodando) {
            System.out.println("\n======= BATTLE CITY =======");
            System.out.println("1 - Jogar (Aleatorio)");
            System.out.println("2 - Escolher Mapa");
            System.out.println("3 - Sair");
            System.out.print("Opcao: ");
            
            if (teclado.hasNextInt()) {
                int opcao = teclado.nextInt();
                String nomeArquivo = null;

                if (opcao == 1) {
                    int num = sorteio.nextInt(3) + 1;
                    nomeArquivo = "Mapa" + num + ".txt";
                    System.out.println("Sorteado: " + nomeArquivo);
                } else if (opcao == 2) {
                    System.out.print("Digite o numero do mapa (1, 2 ou 3): ");
                    if(teclado.hasNextInt()){
                        int num = teclado.nextInt();
                        nomeArquivo = "Mapa" + num + ".txt";
                    } else {
                        System.out.println("Numero invalido!");
                        teclado.next(); 
                    }
                } else if (opcao == 3) {
                    System.out.println("Saindo...");
                    rodando = false;
                } else {
                    System.out.println("Opcao invalida!");
                }

                if (nomeArquivo != null && rodando) {
                    pontuacao = 0; //zera pontos ao começar
                    mapa.carregarMapa(nomeArquivo);
                    mapa.posicionarElementos(); 

                    Tanque jogador = null;
                    Inimigo inimigo = null;
                    char[][] grade = mapa.getGrade();
                    
                    for(int l = 0; l < 13; l++){
                        for(int c = 0; c < 13; c++){
                            if(grade[l][c] == 'T') jogador = new Tanque(l, c);
                            if(grade[l][c] == 'I') inimigo = new Inimigo(l, c);
                        }
                    }

                    boolean jogando = true;
                    while(jogando) {
                        
                        //sistema de pontuação
                        //limpa a tela
                        for(int i=0; i<30; i++) System.out.println();
                        
                        System.out.println("========================================");
                        //pega a vida atual para mostrar
                        int vidaLoop = (jogador != null) ? jogador.getVida() : 0;
                        System.out.println("  VIDAS: " + vidaLoop + "  |  PONTOS: " + pontuacao);
                        System.out.println("========================================");
                        
                        mapa.imprimir(); //imprime o mapa
                        
                        //checa a vitória
                        if (inimigo != null && inimigo.getVida() <= 0) {
                            pontuacao += 100; //atualiza a variável
                            
                            //redesenha a tela de pontuação
                            for(int i=0; i<30; i++) System.out.println();
                            
                            System.out.println("========================================");
                            int vidaFinal = (jogador != null) ? jogador.getVida() : 0;
                            System.out.println("  VIDAS: " + vidaFinal + "  |  PONTOS: " + pontuacao);
                            System.out.println("========================================");
                            
                            mapa.imprimir();
                            
                            System.out.println("\n>>> PARABENS! INIMIGO DESTRUIDO! <<<");
                            System.out.println("Pressione Enter para voltar ao menu...");
                            
                            teclado.nextLine(); //limpa buffer
                            teclado.nextLine(); //espera o Enter
                            
                            jogando = false;
                            break;
                        }

                        //mover o jogador
                        System.out.println("Comandos: w,a,s,d (Mover) | q (Atirar) | x (Sair)");
                        String entrada = teclado.next();
                        char comando = entrada.charAt(0);
                        boolean fimDeJogo = false;

                        if (comando == 'x') {
                            jogando = false;
                        } 
                        else if (comando == 'q') {
                            if(jogador != null) fimDeJogo = jogador.atirar(mapa, jogador, inimigo);
                        } 
                        else {
                            if(jogador != null) jogador.mover(comando, mapa);
                        }

                        if (fimDeJogo) { jogando = false; break; }

                        //parte do inimigo
                        if (jogando && inimigo != null && inimigo.getVida() > 0) {
                            inimigo.moverAleatorio(mapa, jogador);
                            if (jogador.getVida() <= 0) {
                                //se você morreu no turno do inimigo:
                                System.out.println(">>> VOCE PERDEU! <<<");
                                System.out.println("Pressione Enter...");
                                teclado.nextLine();
                                teclado.nextLine();
                                jogando = false;
                            }
                        }
                    }
                }
            } else {
                teclado.next(); 
            }
        }
        teclado.close();
    }
}