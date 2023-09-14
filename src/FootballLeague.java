import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Team {
    private String name; // team name
    private int wins; // number of wins
    private int draws; // number of draws
    private int losses; // number of losses
    private int goalsFor; // goals scored
    private int goalsAgainst; // goals conceded

    public Team(String name) {
        this.name = name; // assigns the team's name from the parameter
    }

    public String getName() {
        return name; // returns the team's name
    }

    public int getPoints() {
        return wins * 3 + draws; // calculates and returns the team's total points
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst; // calculates and returns the goal difference
    }

    public void incrementWins() {
        wins++; // increments the number of wins
    }

    public void incrementDraws() {
        draws++; // increments the number of draws
    }

    public void incrementLosses() {
        losses++; // increments the number of losses
    }

    public void incrementGoalsFor(int goals) {
        goalsFor += goals; // increments the number of goals scored
    }

    public void incrementGoalsAgainst(int goals) {
        goalsAgainst += goals; // increments the number of goals conceded
    }

    @Override
    public String toString() {
        return name + " - Points: " + getPoints() + ", Goal Difference: " + getGoalDifference(); // returns the team's name, points and goal difference
    }
}

class Fixture {
    private Team homeTeam;
    private Team awayTeam;

    public Fixture(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam; // sets the home team
        this.awayTeam = awayTeam; // sets the away team
    }

    public Team getHomeTeam() {
        return homeTeam; // returns the home team
    }

    public Team getAwayTeam() {
        return awayTeam; // returns the away team
    }
}

class League {
    private List<Team> teams; // list to store teams
    private List<Fixture> fixtures; // list to store fixtures
    private int numTeams; // number of teams in the league

    public League(int numTeams) {
        this.numTeams = numTeams; // sets the number of teams for the league
        teams = new ArrayList<>(); // initializes the list of teams
        fixtures = new ArrayList<>(); // initializes the list of fixtures
    }

    public void addTeam(Team team) {
        teams.add(team); // adds a team to the league
    }

    public List<Fixture> getFixtures() {
        return fixtures; // returns the list of fixtures in the league
    }

    public void generateFixtures() {
        if (teams.size() != numTeams) {
            System.out.println("Not all teams have been added to the league.");
            return;
        }

        Collections.shuffle(teams); // shuffles the teams

        for (int i = 0; i < numTeams; i++) {
            Team homeTeam = teams.get(i); // gets the home team
            Team awayTeam;

            for (int j = 0; j < numTeams - 1; j++) {
                int awayIndex = (i + j + 1) % numTeams; // calculates the index of the away team
                awayTeam = teams.get(awayIndex); // gets the away team

                Fixture fixture = new Fixture(homeTeam, awayTeam); // creates a fixture
                fixtures.add(fixture); // adds the fixture to the list
            }
        }
    }

    public void simulateMatch(Fixture fixture) {
        Team homeTeam = fixture.getHomeTeam(); // gets the home team
        Team awayTeam = fixture.getAwayTeam(); // gets the away team

        Random random = new Random();
        int homeGoals = random.nextInt(5); // generates a random number of goals scored by the home team
        int awayGoals = random.nextInt(5); // generates a random number of goals scored by the away team

        homeTeam.incrementGoalsFor(homeGoals); // updates goals scored by the home team
        homeTeam.incrementGoalsAgainst(awayGoals); // updates goals conceded by the home team
        awayTeam.incrementGoalsFor(awayGoals); // updates goals scored by the away team
        awayTeam.incrementGoalsAgainst(homeGoals); // updates goals conceded by the away team

        if (homeGoals > awayGoals) {
            homeTeam.incrementWins(); // updates the number of wins for the home team
            awayTeam.incrementLosses(); // updates the number of losses for the away team
        } else if (homeGoals < awayGoals) {
            homeTeam.incrementLosses(); // updates the number of losses for the home team
            awayTeam.incrementWins(); // updates the number of wins for the away team
        } else {
            homeTeam.incrementDraws(); // updates the number of draws for the home team
            awayTeam.incrementDraws(); // updates the number of draws for the away team
        }
    }

    public void displayStandings() {
        System.out.println("League Standings:");

        Collections.sort(teams, (t1, t2) -> {
            int pointsDiff = t2.getPoints() - t1.getPoints(); // calculates the points difference between teams
            if (pointsDiff != 0) {
                return pointsDiff; // sorts teams by points difference if it's not zero
            } else {
                return t2.getGoalDifference() - t1.getGoalDifference(); // sorts teams by goal difference if points difference is zero
            }
        });

        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i); // gets the sorted teams
            System.out.println((i + 1) + ". " + team); // prints teams in sorted order
        }
    }
}

public class FootballLeague {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // get the number of teams in the league from the user
        System.out.print("Enter the number of teams in the league: ");
        int numTeams = scanner.nextInt();
        scanner.nextLine();

        League league = new League(numTeams);

        for (int i = 0; i < numTeams; i++) {
            // get the names of the teams
            System.out.print("Enter the name of team " + (i + 1) + ": ");
            String teamName = scanner.nextLine();
            Team team = new Team(teamName);
            league.addTeam(team);
        }

        league.generateFixtures();

        System.out.println("Fixtures generated:");
        List<Fixture> fixtures = league.getFixtures();
        for (int i = 0; i < fixtures.size(); i++) {
            Fixture fixture = fixtures.get(i);
            // print the fixtures
            System.out.println("Week " + (i + 1) + ": " + fixture.getHomeTeam().getName() + " vs " + fixture.getAwayTeam().getName());
        }

        for (Fixture fixture : fixtures) {
            System.out.println("Enter the result for the match between " + fixture.getHomeTeam().getName() + " and " + fixture.getAwayTeam().getName());
            System.out.print("Goals for " + fixture.getHomeTeam().getName() + ": ");
            int homeGoals = scanner.nextInt();
            System.out.print("Goals for " + fixture.getAwayTeam().getName() + ": ");
            int awayGoals = scanner.nextInt();
            scanner.nextLine();

            // update goal statistics for teams
            fixture.getHomeTeam().incrementGoalsFor(homeGoals);
            fixture.getHomeTeam().incrementGoalsAgainst(awayGoals);
            fixture.getAwayTeam().incrementGoalsFor(awayGoals);
            fixture.getAwayTeam().incrementGoalsAgainst(homeGoals);

            // update team statistics based on match result
            if (homeGoals > awayGoals) {
                fixture.getHomeTeam().incrementWins();
                fixture.getAwayTeam().incrementLosses();
            } else if (homeGoals < awayGoals) {
                fixture.getHomeTeam().incrementLosses();
                fixture.getAwayTeam().incrementWins();
            } else {
                fixture.getHomeTeam().incrementDraws();
                fixture.getAwayTeam().incrementDraws();
            }

            league.displayStandings(); // display the league standings
        }
    }
}