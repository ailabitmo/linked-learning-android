package com.ifmo.LinkedLearning.model.operations;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 13.11.13
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
public class SPARQLQueryHelper {

    public static String  queryALLCourses() {
            return "select distinct ?uri ?name " +
            "where { ?uri rdf:type aiiso:Course . " +
            "FILTER regex(str(?uri), \"^http://www.semanticweb.org/k0shk/ontologies\") .    " +
            "OPTIONAL {?uri rdfs:label ?name . " +
            "      FILTER (langMatches(lang(?name), \"ru\")) } " +
            "   OPTIONAL {?uri rdfs:label ?name } }";
    }

    public static String  queryALLModules() {
        return "SELECT ?uri ?name ?number ?parent WHERE {\n" +
                "        ?uri rdf:type aiiso:Module .\n" +
                "   { ?parent learningRu:hasModule ?uri } UNION { ?uri learningRu:isModuleOf  ?parent}\n" +
                "        ?uri learningRu:numberOfModule  ?number .\n" +
                "   OPTIONAL {?uri rdfs:label ?name . \n" +
                "      FILTER (langMatches(lang(?name), \"ru\")) } \n" +
                "   OPTIONAL {?uri rdfs:label ?name } }";
    }

    public static String  queryALLLectures() {
        return "SELECT ?uri ?name ?number ?parent WHERE {\n" +
                "        ?uri rdf:type learningRu:Lecture .\n" +
                "   { ?parent learningRu:hasLecture ?uri } UNION { ?uri learningRu:isLectureOf  ?parent}\n" +
                "        ?uri learningRu:numberOfLecture  ?number .\n" +
                "   OPTIONAL {?uri rdfs:label ?name . \n" +
                "      FILTER (langMatches(lang(?name), \"ru\")) } \n" +
                "   OPTIONAL {?uri rdfs:label ?name } }";
    }

    public static String  queryALLTerms() {
        return "SELECT ?uri ?name  ?parent WHERE {\n" +
                "        ?uri rdf:type learningRu:Term .\n" +
                "   { ?parent learningRu:hasTerm ?uri } UNION { ?uri learningRu:isTermOf  ?parent}\n" +
                "        OPTIONAL {?uri rdfs:label ?name . \n" +
                "      FILTER (langMatches(lang(?name), \"ru\")) } \n" +
                "   OPTIONAL {?uri rdfs:label ?name } }";
    }

    public static String  queryALLBibos() {
        return "SELECT ?uri ?name ?parent ?author_list ?pdf ?publication WHERE {\n" +
                "        ?uri rdf:type bibo:Book .\n" +
                "   OPTIONAL { ?uri bibo:authorList ?author_list }\n" +
                "   OPTIONAL { ?uri dcterms:publisher ?publication }\n" +
                "   OPTIONAL { ?uri dc:source ?pdf }\n" +
                "        FILTER regex(str(?uri), \"^http://books.ifmo.ru\") .\n" +
                "   \n" +
                "   OPTIONAL { { ?parent learningRu:hasResource ?uri } UNION { ?uri learningRu:isResourceOf  ?parent}}\n" +
                "        OPTIONAL {?uri rdfs:label ?name . \n" +
                "      FILTER (langMatches(lang(?name), \"ru\")) } \n" +
                "   OPTIONAL {?uri rdfs:label ?name } }";
    }
}
