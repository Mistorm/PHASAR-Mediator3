package nl.ru.cs.phasar.mediator.documentsource.flatfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.Hit;
import nl.ru.cs.phasar.mediator.documentsource.Result;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class FlatSource implements DocumentSource {

    private static final String FILEPATH = "/home/mistorm/workspaces/netbeans/Mediator3/src/main/resources/examplesents.txt";
    private List<Result> sents;
    private char newChar;

    public FlatSource() {

        this.sents = new ArrayList<Result>();

        try {
            parseFile(FILEPATH);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FlatSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parseFile(String filePath) throws FileNotFoundException {

        String line = null;
        Result currentSent = null;
        String[] currentTriple = null;

        File file = new File(filePath);

        Scanner scan = new Scanner(file);

        while (scan.hasNext()) {
            line = scan.nextLine();
            //If a line starts with a [, it contains a ground triple.
            if (line.startsWith("[")) {
                currentTriple = line.split(",");

                //TODO: This is.. ugly. Should be easier?
                for (int i = 0; i < currentTriple.length; i++) {
                    currentTriple[i] = currentTriple[i].replace('[', newChar);
                    currentTriple[i] = currentTriple[i].replace(']', newChar);
                    currentTriple[i] = currentTriple[i].trim();
                }

                currentSent.addTriple(new Triple(currentTriple));
            } //No [, so it must be a sentece.
            else {
                if (currentSent != null) {
                    sents.add(currentSent);
                }
                currentSent = new Result(line);
            }

            line = null;
        }
    }

    @Override
    public List<Hit> getDocuments(Metadata metadata, List<Triple> triples) {

        List<Triple> matches = new ArrayList<Triple>();
        List<Hit> hitList = new ArrayList<Hit>();

        //Searching without the triple(s) to find is imposible
        if (triples.isEmpty()) {
            return hitList;
        }

        for (Result sent : sents) {
            matches = new ArrayList<Triple>();
            for (Triple triple : triples) {
                matches.addAll(sent.matchTriple(triple));
            }
            if (matches.size() >= triples.size()) {
                hitList.add(new Hit(sent, matches));
            }
        }

        System.out.println("Nr. of sents found: " + hitList.size());

        return hitList;
    }
}
