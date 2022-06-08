public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img)
    {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p)
    {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p)
    {
        return Math.sqrt((xxPos - p.xxPos)*(xxPos - p.xxPos) + (yyPos - p.yyPos)*(yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p)
    {
        double r = calcDistance(p);
        return G * mass * p.mass / (r * r);
    }

    public double calcForceExertedByX(Planet p)
    {
        double F = calcForceExertedBy(p);
        return F * (p.xxPos - xxPos) / calcDistance(p);
    }

    public double calcForceExertedByY(Planet p)
    {
        double F = calcForceExertedBy(p);
        return F * (p.yyPos - yyPos) / calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet [] all)
    {
        double F = 0;
        for(Planet p : all)
        {
            if(this.equals(p))
            {
                continue;
            }
            F += calcForceExertedByX(p);
        }
        return F;
    }

    public double calcNetForceExertedByY(Planet [] all)
    {
        double F = 0;
        for(Planet p : all)
        {
            if(this.equals(p))
            {
                continue;
            }
            F += calcForceExertedByY(p);
        }
        return F;
    }

    public void update(double dt,double fX,double fY)
    {
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw()
    {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }

}