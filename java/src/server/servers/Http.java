package server.servers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;

import server.handlers.*;


public class Http implements iServer {
    public void start() throws IOException{
        int port = 8081;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        // Swagger Endpoints
        server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
        server.createContext("/docs/api/view", new Handlers.BasicFile(""));

        // Project Endpoints
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/games/list", new ListHandler());
        server.createContext("/games/create", new CreateHandler());
        server.createContext("/games/join", new JoinHandler());
        server.createContext("/game/model", new ModelHandler());
        server.createContext("/game/addAI", new AddAIHandler());
        server.createContext("/game/listAI", new ListAIHandler());
        server.createContext("/moves/sendChat", new SendChatHandler());
        server.createContext("/moves/rollNumber", new RollNumberHandler());
        server.createContext("/moves/robPlayer", new RobPlayerHandler());
        server.createContext("/moves/finishTurn", new FinishTurnHandler());
        server.createContext("/moves/buyDevCard", new BuyDevCardHandler());
        server.createContext("/moves/Year_of_Plenty", new YearOfPlentyHandler());
        server.createContext("/moves/Road_Building", new RoadBuildingHandler());
        server.createContext("/moves/Soldier", new SoldierHandler());
        server.createContext("/moves/Monopoly", new MonopolyHandler());
        server.createContext("/moves/Monument", new MonumentHandler());
        server.createContext("/moves/buildRoad", new BuildRoadHandler());
        server.createContext("/moves/buildSettlement", new BuildSettlementHandler());
        server.createContext("/moves/buildCity", new BuildCityHandler());
        server.createContext("/moves/offerTrade", new OfferTradeHandler());
        server.createContext("/moves/acceptTrade", new AcceptTradeHandler());
        server.createContext("/moves/maritimeTrade", new MaritimeTradeHandler());
        server.createContext("/moves/discardCards", new DiscardCardsHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
