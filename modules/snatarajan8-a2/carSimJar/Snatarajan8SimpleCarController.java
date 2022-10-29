import java.util.*;
import java.awt.geom.*;
import java.awt.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Snatarajan8SimpleCarController implements CarController {

    double acc; // Acceleration.
    double vel; // Velocity.
    double phi; // Steering angle.
    double mu1=0, mu2=1;
    boolean isAccelModel = false;
    ArrayList<Rectangle2D.Double> obstacles;
    SensorPack sensors;
    double endX;
    double endY;
    double initX;
    double initY;
    final double gridSize = 42;
    double leftEdge;
    double rightEdge;
    double topEdge;
    double botEdge;
    int[][] grid;
    int startX = -1;
    int startY = -1;
    int goalX = -1;
    int goalY = -1;

    int[][] movements = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
    int[][] path;
    int[][] coordinatePath;

    @Override
    public void init(double initX, double initY, double initTheta, double endX, double endY, double endTheta, ArrayList<Rectangle2D.Double> obstacles, SensorPack sensors) {
        this.obstacles = obstacles;
        this.sensors = sensors;
        this.initX = initX;
        this.initY = initY;
        this.endX = endX;
        this.endY = endY;

        this.path = null;
        this.coordinatePath = null;

        this.startX = -1;
        this.startY = -1;
        this.goalX = -1;
        this.goalY = -1;

        int obstacleLength = obstacles.size();

        for (int i = 0; i < obstacleLength; i++) {
            System.out.println(obstacles.get(i));
        }

        System.out.println(initX);
        System.out.println(initY);
        System.out.println(endX);
        System.out.println(endY);

//        java.awt.geom.Rectangle2D$Double[x=50.0,y=400.0,w=200.0,h=150.0]
//        java.awt.geom.Rectangle2D$Double[x=100.0,y=200.0,w=200.0,h=150.0]
//        java.awt.geom.Rectangle2D$Double[x=320.0,y=450.0,w=50.0,h=400.0]
//        java.awt.geom.Rectangle2D$Double[x=0.0,y=2.0,w=1000.0,h=10.0]
//        java.awt.geom.Rectangle2D$Double[x=0.0,y=476.0,w=1000.0,h=10.0]
//        java.awt.geom.Rectangle2D$Double[x=998.0,y=468.0,w=10.0,h=468.0]
//        java.awt.geom.Rectangle2D$Double[x=-8.0,y=468.0,w=10.0,h=468.0]
//        50.0
//        50.0
//        290.0
//        350.0
        // Sample input

        // Create grid
        int width = (int)obstacles.get(obstacleLength-3).width;
        int length = (int)obstacles.get(obstacleLength-1).height;

        this.grid = new int[width][length];

        for (int i = 0; i < width / this.gridSize; i++) {
            for (int j = 0; j < length / this.gridSize; j++) {
                double x = (i + 0.5) * this.gridSize;
                double y = (j + 0.5) * this.gridSize;
                boolean flag = false;
                for (int k = 0; k < obstacleLength; k++) {
                    Rectangle2D.Double obstacle = obstacles.get(k);
                    if (x > obstacle.x && x < obstacle.x + obstacle.width &&
                            y > obstacle.y && y < obstacle.y + obstacle.height) {
                        grid[i][j] = -1;
                        flag = true;
                        break;
                    }
                }
                if (endX > x && endX < x + this.gridSize && endY > y && endY < y + this.gridSize) {
                    grid[i][j] = 1;
                    this.goalX = i;
                    this.goalY = j;
                    flag = true;
                    System.out.println("GOAL");
                    System.out.println(i + " " + j + " " + this.grid[i][j] + " " + x + " " + y);
                }
                if (initX > x && initX < x + this.gridSize && initY > y && initY < y + this.gridSize) {
                    grid[i][j] = 2;
                    this.startX = i;
                    this.startY = j;
                    flag = true;
                }
                if (!flag) {
                    grid[i][j] = 0;
                }
            }
        }

//        for (int i = 0; i < width / this.gridSize; i++) {
//            for (int j = 0; j < length / this.gridSize; j++) {
//                System.out.println(i + " " + j + " " + this.grid[i][j]);
//            }
//        }

        // Calculate path using gridSize
        int curX = this.startX;
        int curY = this.startY;
//        HashSet<int[]> visited = new HashSet<int[]>{new int[]{curX, curY}};

        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if (getPointDistance(a) <= getPointDistance(b)) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        HashMap<int[], int[][]> paths = new HashMap<int[], int[][]>();
        paths.put(new int[]{this.startX, this.startY}, null);
        queue.add(new int[]{this.startX, this.startY});

        while (queue.size() != 0) {
            // Snatarajan8DubinCarController
            int[] cur = queue.poll();
            System.out.println("Polling the node " + cur[0] + " " + cur[1] + " " + grid[cur[0]][cur[1]]);
            if (grid[cur[0]][cur[1]] == -1) {
                continue;
            }
            int[][] x = paths.getOrDefault(cur, new int[][]{{}});
//            if (!Arrays.equals(x,new int[][]{{}}) || Arrays.equals(cur,new int[]{this.startX, this.startY})) {
//                System.out.println(cur[0] + " " + cur[1] + " " + this.startX + " " + this.startY);
//                System.out.println(x.length);
//                System.out.println(queue.size());
//                continue;
//            }
            int [][] neighbors = new int[4][2];
            int counter = 0;
            for (int i = 0; i < 4; i++) {
                if (cur[0] + movements[i][0] == width || cur[1] + movements[i][1] == length ||
                        cur[0] + movements[i][0] < 0 || cur[1] + movements[i][1] < 0) {
                    continue;
                } else {
//                    System.out.println("These are the neighbors and cur - ");
//                    System.out.println(cur[0] + " " + cur[1] + " " + neighbors);
                    neighbors[counter][0] = cur[0] + movements[i][0];
                    neighbors[counter][1] = cur[1] + movements[i][1];
                    queue.add(neighbors[counter]);
                    System.out.println("Adding to queue " + neighbors[counter][0] + " " + neighbors[counter][1]);
                    counter += 1;
                }
            }

            for (int i = 0; i < neighbors.length; i++) {
                int[][] movement = new int[neighbors.length][2];
                movement[i][0] = neighbors[i][0] - cur[0];
                movement[i][1] = neighbors[i][1] - cur[1];
                if (grid[neighbors[i][0]][neighbors[i][1]] == -1 || grid[neighbors[i][0]][neighbors[i][1]] == 2) {
                    // Skip start or obstacle
                    continue;
                } else if (grid[neighbors[i][0]][neighbors[i][1]] == 1) {
                    // If goal
                    int pathLength = paths.get(cur).length;
                    this.path = new int[pathLength+1][2];
                    int[][] curPath = paths.get(cur);
                    System.out.println("The final path - ");
                    for (int j = 0; j < pathLength; j++) {
                        System.out.println(curPath[j][0] + " " + curPath[j][1]);
                        this.path[j] = curPath[j];
                    }
                    this.path[pathLength] = movement[i];
                    break;
                } else {
                    // This means the neighbor is not goal, start or obstacle
                    int[][] path = paths.getOrDefault(neighbors[i], null);
                    int[][] isPath = paths.getOrDefault(cur, null);
                    int pathLength;
                    if (isPath == null || isPath.equals(new int[][]{{}})) {
                        pathLength = 0;
                    } else {
                        pathLength = isPath.length;
                    }
                    if (path == null) {
                        // This means this is the first time we are exploring this place
                        queue.add(neighbors[i]);

                        if (pathLength == 0) {
                            // Exploring a node adjacent to the root
                            paths.put(neighbors[i], new int[][]{movement[i]});
                        } else {
//                            System.out.println("This is the length of the path - " + pathLength);
                            int[][] newPath = new int[pathLength+1][2];
                            int[][] curPath = paths.get(cur);
//                            System.out.println("This is the path - ");
                            for (int j = 0; j < pathLength; j++) {
                                newPath[j] = curPath[j];
                                System.out.println("Updating path - ");
                                System.out.println(curPath[j][0] + " " + curPath[j][1]);
                            }
                            newPath[pathLength] = movement[i];
//                            int[][] newPath = Arrays.copyOf(paths.get(cur), pathLength+1);
//                            newPath[pathLength] = movements[i];
                            paths.put(neighbors[i], newPath);
                        }

                    } else if (path.length > paths.get(cur).length + 1) {
                        queue.add(neighbors[i]);
                        System.out.println("Length of the path to neighbor - " + path.length);

                        System.out.println("Length of the path to cur - " +
                                paths.get(cur));

                        int[][] newPath = new int[pathLength+1][2];
                        int[][] curPath = paths.get(cur);
                        for (int j = 0; j < pathLength; j++) {
                            newPath[j] = curPath[j];
                        }
                        newPath[pathLength] = movements[i];
                        paths.replace(neighbors[i], newPath);
                    }
                }
            }

            if (this.path != null) {
                break;
            }
        }
        if (this.path == null || Arrays.equals(this.path, new int[][]{{}})) {
            System.out.println("Whoops");
        }
        int pathX = this.startX;
        int pathY = this.startY;
        System.out.println("Path starts here - ");
        System.out.println(pathX + " " + pathY);
        coordinatePath = new int[this.path.length][2];
        for (int i = 0; i < this.path.length; i++) {
            pathX += this.path[i][0];
            pathY += this.path[i][1];
            System.out.println(pathX + " " + pathY);
            coordinatePath[i] = new int[]{pathX, pathY};
        }

    }

//    public int[] popFromQueue(int[][] queue) {
//        for (int i = 0;)
//        return queue[0];
//    }

    public double getPointDistance(int[] cur) {
        int curx = cur[0];
        int cury = cur[1];
        return Math.pow(Math.pow(this.goalX - curx,2) + Math.pow(this.goalY - cury,2),0.5);
    }

    public boolean distanceComparator(int[] a, int[] b) {
        return false;
    }

    @Override
    public void draw(Graphics2D g2, Dimension D) {

    }

    @Override
    public void move() {
//         if (isAccelModel) {
//           this.acc = this.getControl(1);
//           this.vel += this.acc;
//         } else {
//           this.vel = this.getControl(1);
//         }
        double timeForGrid = this.gridSize / 10;
        double time = this.sensors.getTime();
        int index = (int)Math.floor(time / timeForGrid);
        System.out.println(index);
        if (index < path.length) {
            int[] movement = this.path[index];
            int newPhi;
            if (Arrays.equals(movement, this.movements[0])) {
                newPhi = 0;
            } else if (Arrays.equals(movement, this.movements[1])) {
                newPhi = 180;
            } else if (Arrays.equals(movement, this.movements[2])) {
                newPhi = 90;
            } else {
                newPhi = -90;
            }
            if (this.phi != newPhi) {
                this.vel = 1;
            } else {
                this.vel = 10;
            }

        }


    }

    @Override
    public double getControl(int i) {
        if (i == 1) {
            if (isAccelModel) {
                return acc;
            } else {
                return vel;
            }
        } else if (i == 2) {
            return phi;
        }
        return 0;
    }
}
