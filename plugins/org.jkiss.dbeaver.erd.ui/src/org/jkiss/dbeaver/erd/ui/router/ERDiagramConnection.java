package org.jkiss.dbeaver.erd.ui.router;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Geometry;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.jkiss.dbeaver.erd.ui.ERDUIConstants;
import org.jkiss.dbeaver.ui.UIUtils;

public class ERDiagramConnection extends PolylineConnection {
    private static final double DELTA = 0.15;
    private static final int TOLERANCE = 2;

    private boolean selected;
    private boolean bezier;

    public ERDiagramConnection(boolean bezier) {
        this.bezier = bezier;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setBezier(boolean bezier) {
        this.bezier = bezier;
    }

    @Override
    protected void outlineShape(Graphics g) {
        g.setAntialias(SWT.ON);

//        g.setBForegroundColor(ColorConstants.black);
//        g.setLineWidth(1);

        if (selected) {
            if (bezier) {
                g.setForegroundColor(ColorConstants.gray);

                final PointList points = getPoints();
                g.drawPolyline(points);

            }

            g.setForegroundColor(ColorConstants.blue);
            g.setLineWidth(4);
        }

        // final PointList points = getBezierPoints();
//        g.setForegroundColor(ColorConstants.blue);
        final PointList points = getOrthoPoints();
      
       
        //g.setBackgroundColor(UIUtils.getColorRegistry().get(ERDUIConstants.COLOR_ERD_LINES_FOREGROUND));
//       g.setBackgroundColor(ColorConstants.darkBlue);
//       Point firstPoint = points.getPoint(1);
//       g.fillOval(firstPoint.x-2, firstPoint.y-2, 4, 4);

        // Point lastPoint = points.getPoint(points.size() - 2);
        // g.fillOval(lastPoint.x - 3, lastPoint.y - 3, 6, 6);

//        final PointList triangle = new PointList();
//        triangle.addPoint(lastPoint.x+8 , lastPoint.y-1 );
//        triangle.addPoint(lastPoint.x-3, lastPoint.y+5 );
//        triangle.addPoint(lastPoint.x-3, lastPoint.y-5 );
//        triangle.addPoint(lastPoint.x+8 , lastPoint.y );
//        g.fillPolygon(triangle);

        // g.fillRectangle(lastPoint.x - 2, lastPoint.y - 2, 4, 4);

        int width = g.getLineWidth();

        Color color = g.getForegroundColor();

        final int lineRed = color.getRed();
        final int lineGreen = color.getGreen();
        final int lineBlue = color.getBlue();

        final int deltaRed = (255 - lineRed) * 2 / width;
        final int deltaGreen = (255 - lineGreen) * 2 / width;
        final int deltaBlue = (255 - lineBlue) * 2 / width;

        int red = 255;
        int green = 255;
        int blue = 255;

        while (width > 0) {
            red -= deltaRed;
            green -= deltaGreen;
            blue -= deltaBlue;

            if (red < lineRed) {
                red = lineRed;
            }
            if (green < lineGreen) {
                green = lineGreen;
            }
            if (blue < lineBlue) {
                blue = lineBlue;
            }

            color = new Color(Display.getCurrent(), red, green, blue);

            g.setLineWidth(width);
            g.setForegroundColor(color);
            g.drawPolyline(points);

            width -= 2;
        }
        // 1
        g.setForegroundColor(UIUtils.getColorRegistry().get(ERDUIConstants.COLOR_ERD_LINES_FOREGROUND));
        g.setBackgroundColor(UIUtils.getColorRegistry().get(ERDUIConstants.COLOR_ERD_LINES_FOREGROUND));
        for (int i = 0; i < points.size() - 1; i++) {
            g.fillOval(points.getPoint(i).x - 2, points.getPoint(i).y - 2, 4, 4);
        }
        
        g.setBackgroundColor(ColorConstants.lightGray);
        g.fillOval(points.getMidpoint().x - 5, points.getMidpoint().y - 5, 10, 10);
      //  g.setForegroundColor(UIUtils.getColorRegistry().get(ERDUIConstants.COLOR_ERD_LINES_FOREGROUND));
        g.setBackgroundColor(UIUtils.getColorRegistry().get(ERDUIConstants.COLOR_ERD_LINES_FOREGROUND));
        g.drawOval(points.getMidpoint().x - 5, points.getMidpoint().y - 5, 10, 10);
        g.setForegroundColor(UIUtils.getColorRegistry().get(ERDUIConstants.COLOR_ERD_LINES_FOREGROUND));
        g.setBackgroundColor(UIUtils.getColorRegistry().get(ERDUIConstants.COLOR_ERD_LINES_FOREGROUND));
    }

    private PointList getOrthoPoints() {
        return getPoints();
        
    }

    public PointList getBezierPoints() {
        final PointList controlPoints = getPoints();

        if (bezier && controlPoints.size() >= 3) {
            int index = 0;

            final PointList pointList = new PointList();

            Point p0 = controlPoints.getPoint(index++);
            Point p1 = controlPoints.getPoint(index++);
            Point p2 = null;
            Point nextPoint = controlPoints.getPoint(index++);

            while (true) {
                if (index != controlPoints.size()) {
                    p2 = new Point((p1.x + nextPoint.x) / 2, (p1.y + nextPoint.y) / 2);
                } else {
                    p2 = nextPoint;
                }

                for (double t = 0.0; t <= 1.0; t = t + DELTA) {
                    final Point point = new Point();

                    point.x = (int) (p0.x * (1 - t) * (1 - t) + 2 * p1.x * t * (1 - t) + p2.x * t * t);

                    point.y = (int) (p0.y * (1 - t) * (1 - t) + 2 * p1.y * t * (1 - t) + p2.y * t * t);

                    pointList.addPoint(point);
                }

                pointList.addPoint(p2);

                if (index == controlPoints.size()) {
                    break;
                }

                p0 = p2;
                p1 = nextPoint;
                nextPoint = controlPoints.getPoint(index++);
            }

            return pointList;
        }

        return controlPoints;
    }

    @Override
    protected boolean shapeContainsPoint(int x, int y) {
        return Geometry.polylineContainsPoint(getBezierPoints(), x, y, TOLERANCE);
        // return Geometry.polylineContainsPoint(getBezierPoints(), x, y, TOLERANCE);
    }

}
