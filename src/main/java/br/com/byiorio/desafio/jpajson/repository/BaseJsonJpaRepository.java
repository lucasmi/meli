public abstract class BaseJsonJpaRepository implements IJsonJpaRepository {
    public BaseJsonJpaRepository()  {
        this.configurar();
    }

    @Override
    public void configurar() {
        try {
            if (!Diretorio.verifica(getNomePasta())) {
                Diretorio.criar(getNomePasta());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}