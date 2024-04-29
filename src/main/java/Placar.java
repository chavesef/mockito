import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Placar {
    Armazenamento armazenamento;

    public void adicionaArmazenamento(Armazenamento armazenamento) {
        this.armazenamento = armazenamento;
    }

    public String registra(String usuario, String tipo, int valor) {
        return armazenamento.armazena(usuario, tipo, valor);
    }

    public String pontosUsuario(String usuario) throws IOException {
        List<String> tipos = armazenamento.tiposPontos(usuario);
        return montaStringPontos(usuario, tipos);
    }

    public String ranquear(String tipo) throws IOException {
        List<String> usuarios = armazenamento.recuperaUsuarios();
        Map<String, Integer> map = new HashMap<>();
        String resultado = "";
        for (String usuario : usuarios) {
            map.put(usuario, armazenamento.recuperaPontosUsuario(usuario, tipo));
        }
        Map<String, Integer> mapOrdenado =
                map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        ));

        int contador = 0;
        for(String usuario : mapOrdenado.keySet()) {
            if (mapOrdenado.get(usuario) > 0) {
                if (contador == 0)
                    resultado = resultado + usuario + ": " + mapOrdenado.get(usuario);
                else
                    resultado = resultado + ", " + usuario + ": " + mapOrdenado.get(usuario);
                contador++;
            }
        }
        return resultado;
    }

    private String montaStringPontos(String usuario, List<String> tipos) throws IOException {
        String resultado = "";
        int contador = 0;
        for (String tipo : tipos) {
            if(contador==0)
                resultado = resultado + tipo + ": " +
                        armazenamento.recuperaPontosUsuario(usuario, tipo);
            else{
                resultado = resultado + ", " + tipo + ": " +
                        armazenamento.recuperaPontosUsuario(usuario, tipo);
            }
            contador++;
        }
        return resultado;
    }
}
