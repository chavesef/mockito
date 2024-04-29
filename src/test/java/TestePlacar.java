import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TestePlacar {
    Placar p;
    Armazenamento mock = Mockito.mock(Armazenamento.class);

    @Before
    public void setUp() {
        p = new Placar();
    }

    @Test
    public void registrarPonto(){
        p.adicionaArmazenamento(mock);
        when(mock.armazena("Elias", "ponto", 10))
                .thenReturn("Ponto registrado com sucesso");
        assertEquals("Ponto registrado com sucesso",
                p.registra("Elias", "ponto", 10));
    }

    @Test
    public void retornarPontosUsuario() throws IOException {
        p.adicionaArmazenamento(mock);
        p.registra("Elias", "ponto", 10);
        p.registra("Elias", "ponto", 15);
        p.registra("Elias", "estrela", 10);
        p.registra("Chicken Little", "energia", 20);
        p.registra("Calleri", "ponto", 30);
        when(mock.tiposPontos("Elias"))
                .thenReturn(List.of("estrela", "ponto"));
        when(mock.recuperaPontosUsuario("Elias", "ponto"))
                .thenReturn(25);
        when(mock.recuperaPontosUsuario("Elias", "estrela"))
                .thenReturn(10);
        assertEquals("estrela: 10, ponto: 25",
                p.pontosUsuario("Elias"));
    }

    @Test
    public void ranking() throws IOException {
        p.adicionaArmazenamento(mock);
        p.registra("Elias", "ponto", 10);
        p.registra("Chicken Little", "energia", 20);
        p.registra("Calleri", "ponto", 30);
        p.registra("Calleri", "ponto", 15);
        p.registra("Chicken Little", "ponto", 50);
        when(mock.recuperaUsuarios())
                .thenReturn(List.of("Elias", "Chicken Little", "Calleri"));
        when(mock.recuperaPontosUsuario("Elias", "ponto"))
            .thenReturn(10);
        when(mock.recuperaPontosUsuario("Chicken Little", "ponto"))
            .thenReturn(50);
        when(mock.recuperaPontosUsuario("Calleri", "ponto"))
            .thenReturn(45);
        assertEquals("Chicken Little: 50, Calleri: 45, Elias: 10",
                p.ranquear("ponto"));
    }

    @Test
    public void rankingSemPonto() throws IOException {
        p.adicionaArmazenamento(mock);
        p.registra("Elias", "ponto", 10);
        p.registra("Chicken Little", "energia", 20);
        p.registra("Calleri", "ponto", 30);
        p.registra("Calleri", "ponto", 15);
        p.registra("Chicken Little", "ponto", 50);
        when(mock.recuperaUsuarios())
                .thenReturn(List.of("Elias", "Chicken Little", "Calleri"));
        when(mock.recuperaPontosUsuario("Elias", "energia"))
                .thenReturn(0);
        when(mock.recuperaPontosUsuario("Chicken Little", "energia"))
                .thenReturn(20);
        when(mock.recuperaPontosUsuario("Calleri", "energia"))
                .thenReturn(0);
        assertEquals("Chicken Little: 20",
                p.ranquear("energia"));
    }


}
