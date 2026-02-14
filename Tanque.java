public class Tanque implements Movivel { 
    protected int posicaoX, posicaoY;
    protected char posicao; //variável de direção (w,a,s,d)
    protected char personagem;
    protected int vida; //nova variável
    
    Tanque(int posicaoX, int posicaoY){
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.personagem = 'T';
        this.posicao = 'w';
        this.vida = 1; //jogador morre com 1 tiro (padrão)
    }

    //método para levar dano
    public void receberDano() {
        this.vida--;
    }
    
    public int getVida() {
        return vida;
    }
    
    //recebe o  'mapa' para checar colisão e público
    public void mover(char letra, Mapa mapa){
        letra = Character.toLowerCase(letra);

        //variáveis temporárias para testar o movimento antes de confirmar
        int novoX = posicaoX;
        int novoY = posicaoY;
        char novaDirecao = posicao;

        switch (letra) {
            case 'w':
                novoX--;
                novaDirecao = 'w';
                break;
            case 'a':
                novoY--;
                novaDirecao = 'a';
                break;
            case 's':
                novoX++;
                novaDirecao = 's';
                break;
            case 'd':
                novoY++;
                novaDirecao = 'd';
                break;
            default:
                return; 
        }

        if (mapa.getElemento(novoX, novoY) == '_') {
            
            mapa.getGrade()[posicaoX][posicaoY] = '_';
            
            posicaoX = novoX;
            posicaoY = novoY;
            posicao = novaDirecao;
            
            mapa.getGrade()[posicaoX][posicaoY] = personagem;
            
        } else {
            //se bateu na parede, apenas atualiza a direção (gira o tanque)
            posicao = novaDirecao;
        }
    }
    
    //método atirar
    //recebe 'Mapa' para pegar a grade correta
    public boolean atirar(Mapa mapa, Tanque jogador, Inimigo inimigo) {
        
        //imprimir a direção do tiro na tela
        if (this.personagem == 'T') { //só mostra mensagem se for o Jogador
            String textoDir = "";
            
            if (this.posicao == 'w') textoDir = "CIMA";
            else if (this.posicao == 's') textoDir = "BAIXO";
            else if (this.posicao == 'a') textoDir = "ESQUERDA";
            else if (this.posicao == 'd') textoDir = "DIREITA";
            
            System.out.println("\n>>> TIRO PARA " + textoDir + "... <<<");
            
            //pequena pausa (500ms) para dar tempo de ler a mensagem antes da animação
            try { Thread.sleep(500); } catch (Exception e) {}
        }

        Tiro t1 = new Tiro(posicaoX, posicaoY, this.posicao, 100, mapa.getGrade(), jogador, inimigo);
        return t1.atirar();
    }
}