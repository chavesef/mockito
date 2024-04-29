import java.util.List;

public interface Armazenamento {
    public String armazena(String usuario, String tipo, int pontos);
    public int recuperaPontosUsuario(String usuario, String tipo);
    public List<String> recuperaUsuarios();
    public List<String> tiposPontos(String usuario);
}
