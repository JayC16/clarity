package test;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clarity.match.Match;
import clarity.parser.Peek;
import clarity.parser.ReplayFile;
import clarity.parser.ReplayIndex;

public class Test {

    public static void main(String[] args) throws Exception {

        long tStart = System.currentTimeMillis();

        Logger log = LoggerFactory.getLogger("TEST");

        // last
        ReplayIndex idx = ReplayFile.indexForFile("C:\\Program Files (x86)\\Steam\\steamapps\\common\\dota 2 beta\\dota\\replays\\432850581.dem");

        // TI3 final
        //ReplayIndex idx = ReplayFile.indexForFile("C:\\Program Files (x86)\\Steam\\steamapps\\common\\dota 2 beta\\dota\\replays\\271145478.dem");
        long tIndex = System.currentTimeMillis() - tStart;
        log.info("index built in {}s", tIndex / 1000.0);

        tStart = System.currentTimeMillis();
        Match match = new Match(idx);
        long tPrologue = System.currentTimeMillis() - tStart;
        log.info("prologue applied in {}s", tPrologue / 1000.0);
        
        tStart = System.currentTimeMillis();
        for (Iterator<Peek> i = idx.matchIterator(); i.hasNext();) {
            Peek p = i.next();
            p.apply(match);
        }
        long tMatch = System.currentTimeMillis() - tStart;
        log.info("match applied in {}s", tMatch / 1000.0);
        log.info("total time taken: {}s", (tIndex + tPrologue + tMatch) / 1000.0);

    }

}
