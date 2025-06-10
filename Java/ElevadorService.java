import java.io.FileReader;
import java.util.*;
import com.google.gson.*;

public class ElevadorService implements IElevadorService {
    
    private static class Registro {
        int andar;
        char elevador;
        char turno;
    }

    private List<Registro> registros;

    public ElevadorService() {
        this.registros = carregarRegistros();
    }

    private List<Registro> carregarRegistros() {
        try {
            Gson gson = new Gson();
            Registro[] arr = gson.fromJson(new FileReader("../input.json"), Registro[].class);
            return Arrays.asList(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Integer> andarMenosUtilizado() {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int i = 0; i <= 15; i++) freq.put(i, 0);
        for (Registro r : registros) freq.put(r.andar, freq.get(r.andar) + 1);
        int min = Collections.min(freq.values());
        List<Integer> res = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : freq.entrySet())
            if (e.getValue() == min) res.add(e.getKey());
        return res;
    }

    @Override
    public List<Character> elevadorMaisFrequentado() {
        Map<Character, Integer> freq = new HashMap<>();
        for (char e : new char[]{'A','B','C','D','E'}) freq.put(e, 0);
        for (Registro r : registros) freq.put(r.elevador, freq.get(r.elevador) + 1);
        int max = Collections.max(freq.values());
        List<Character> res = new ArrayList<>();
        for (Map.Entry<Character, Integer> e : freq.entrySet())
            if (e.getValue() == max) res.add(e.getKey());
        return res;
    }

    @Override
    public List<Character> periodoMaiorFluxoElevadorMaisFrequentado() {
        List<Character> elevadores = elevadorMaisFrequentado();
        List<Character> periodos = new ArrayList<>();
        for (char elevador : elevadores) {
            Map<Character, Integer> freq = new HashMap<>();
            for (char t : new char[]{'M','V','N'}) freq.put(t, 0);
            for (Registro r : registros)
                if (r.elevador == elevador) freq.put(r.turno, freq.get(r.turno) + 1);
            int max = Collections.max(freq.values());
            for (Map.Entry<Character, Integer> e : freq.entrySet())
                if (e.getValue() == max) periodos.add(e.getKey());
        }
        return periodos;
    }

    @Override
    public List<Character> elevadorMenosFrequentado() {
        Map<Character, Integer> freq = new HashMap<>();
        for (char e : new char[]{'A','B','C','D','E'}) freq.put(e, 0);
        for (Registro r : registros) freq.put(r.elevador, freq.get(r.elevador) + 1);
        int min = Collections.min(freq.values());
        List<Character> res = new ArrayList<>();
        for (Map.Entry<Character, Integer> e : freq.entrySet())
            if (e.getValue() == min) res.add(e.getKey());
        return res;
    }

    @Override
    public List<Character> periodoMenorFluxoElevadorMenosFrequentado() {
        List<Character> elevadores = elevadorMenosFrequentado();
        List<Character> periodos = new ArrayList<>();
        for (char elevador : elevadores) {
            Map<Character, Integer> freq = new HashMap<>();
            for (char t : new char[]{'M','V','N'}) freq.put(t, 0);
            for (Registro r : registros)
                if (r.elevador == elevador) freq.put(r.turno, freq.get(r.turno) + 1);
            int min = Collections.min(freq.values());
            for (Map.Entry<Character, Integer> e : freq.entrySet())
                if (e.getValue() == min) periodos.add(e.getKey());
        }
        return periodos;
    }

    @Override
    public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
        Map<Character, Integer> freq = new HashMap<>();
        for (char t : new char[]{'M','V','N'}) freq.put(t, 0);
        for (Registro r : registros) freq.put(r.turno, freq.get(r.turno) + 1);
        int max = Collections.max(freq.values());
        List<Character> res = new ArrayList<>();
        for (Map.Entry<Character, Integer> e : freq.entrySet())
            if (e.getValue() == max) res.add(e.getKey());
        return res;
    }

    private float percentualDeUso(char elevador) {
        long total = registros.size();
        long count = registros.stream().filter(r -> r.elevador == elevador).count();
        return Math.round((count * 10000.0f / total)) / 100.0f;
    }

    @Override
    public float percentualDeUsoElevadorA() { return percentualDeUso('A'); }
    @Override
    public float percentualDeUsoElevadorB() { return percentualDeUso('B'); }
    @Override
    public float percentualDeUsoElevadorC() { return percentualDeUso('C'); }
    @Override
    public float percentualDeUsoElevadorD() { return percentualDeUso('D'); }
    @Override
    public float percentualDeUsoElevadorE() { return percentualDeUso('E'); }

    public static void main(String[] args) {
        ElevadorService service = new ElevadorService();

        System.out.println("Andar menos utilizado: " + service.andarMenosUtilizado());
        System.out.println("Elevador mais frequentado: " + service.elevadorMaisFrequentado());
        System.out.println("Período de maior fluxo do(s) elevador(es) mais frequentado(s): " + service.periodoMaiorFluxoElevadorMaisFrequentado());
        System.out.println("Elevador menos frequentado: " + service.elevadorMenosFrequentado());
        System.out.println("Período de menor fluxo do(s) elevador(es) menos frequentado(s): " + service.periodoMenorFluxoElevadorMenosFrequentado());
        System.out.println("Período de maior utilização do conjunto de elevadores: " + service.periodoMaiorUtilizacaoConjuntoElevadores());
        System.out.println("Percentual de uso do elevador A: " + service.percentualDeUsoElevadorA() + "%");
        System.out.println("Percentual de uso do elevador B: " + service.percentualDeUsoElevadorB() + "%");
        System.out.println("Percentual de uso do elevador C: " + service.percentualDeUsoElevadorC() + "%");
        System.out.println("Percentual de uso do elevador D: " + service.percentualDeUsoElevadorD() + "%");
        System.out.println("Percentual de uso do elevador E: " + service.percentualDeUsoElevadorE() + "%");
    }
}