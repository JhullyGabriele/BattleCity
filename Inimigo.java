import java.util.Random;

public class Inimigo extends Tanque {
    
    public Inimigo(int posicaoX, int posicaoY){
        super(posicaoX, posicaoY);
        this.personagem = 'I';
        this.vida = 2; //vida do inimigo
    }

    //ia do movimento
    public void moverAleatorio(Mapa mapa, Tanque jogador) {
        Random gerador = new Random();
        int sorteio = gerador.nextInt(100); //sorteia de 0 a 99

        //30% de chance de atirar
        if (sorteio < 30) {
            this.atirar(mapa, jogador, this);
            
        } else {
            //70% de chance de mover
            char direcao = ' ';
            
            //lógica para forçar a descida se estiver muito no topo
            if (this.posicaoX < 3 && gerador.nextBoolean()) {
                //se estiver nas 3 primeiras linhas, tem 50% de chance extra de tentar descer
                direcao = 's';
            } else {
                //caso contrário, sorteia direção normal
                int move = gerador.nextInt(4);
                if (move == 0) direcao = 'w'; // Cima
                if (move == 1) direcao = 's'; // Baixo
                if (move == 2) direcao = 'a'; // Esquerda
                if (move == 3) direcao = 'd'; // Direita
            }
            
            this.mover(direcao, mapa);
        }
    }
}