package com.example.saro.compassn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Saro on 4/15/2015.
 */
public class DrawView extends View {
    private float x1, y1, x2, y2, x3, y3, x4, y4;
    private Paint mPaint1 = new Paint();
    private Paint mPaint2 = new Paint();
    private Path mPath = new Path();
    private float angle = 0f;
    private float x;
    private float y;
    private float epsilon = 60;
    private float lastAzimuth = 0f;
    private float lastAngle = 0f;
    private float cx, cy, radius;
    private float temp1, temp2;
    private float temp;
    private boolean test = true;
    private boolean testOfLastAngle = true;


    public DrawView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint1.setAntiAlias(true);
        mPaint1.setStrokeWidth(3);
        mPaint1.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint1.setColor(Color.YELLOW);

        mPaint2.setAntiAlias(true);
        mPaint2.setStrokeWidth(0);
        mPaint2.setTextSize(27);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(android.graphics.Color.BLACK);
    }

    private void getPeaksOfRhumb() {
        //if (testOfLastAngle) {
            x2 = (float) (cx + radius * Math.sin((double) (-angle + 90) / 180 * Math.PI));
            y2 = (float) (cy - radius * Math.cos((double) (-angle + 90) / 180 * Math.PI));
            x1 = 2 * cx - x2;
            y1 = 2 * cy - y2;
            x3 = (float) (cx + radius * Math.sin((double) (-angle) / 180 * Math.PI));
            y3 = (float) (cy - radius * Math.cos((double) (-angle) / 180 * Math.PI));
            x4 = 2 * cx - x3;
            y4 = 2 * cy - y3;
        /*} else {

        }*/
    }

    private void determinantPoints() {
        if (angle >= 315 || angle >= 0 && angle < 60) {
            x3 -= 12;//2*10;
            y3 -= 12;//2*10;
            x4 -= 10;//2*8;
            y4 += 32;//2*30;
            x2 += 12;//2*10;
            y2 += 12;//2*10;
            x1 -= 32;//2*20;//2*30;
            y1 += 12;//2*10;
        } else if (angle >= 240 && angle < 315) {
            x3 += 12;//2*10;
            y3 += 12;//2*10;
            x4 -= 32;//2*20;//2*30;
            y4 += 12;//2*10;
            x2 -= 10;//2*8;
            y2 += 32;//2*30;
            x1 -= 12;//2*10;
            y1 -= 12;//2*10;
        } else if (angle < 240 && angle >= 140) {
            x3 -= 10;//2*8;
            y3 += 32;//2*30;
            x4 -= 12;//2*10;
            y4 -= 12;//2*10;
            x2 -= 32;//2*20;//2*30;
            y2 += 12;//2*10;
            x1 += 12;//2*10;
            y1 += 12;//2*10;
        } else if (angle >= 60 && angle < 140) {
            x3 -= 32;//2*20;//2*30;
            y3 += 12;//2*10;
            x4 += 12;//2*10;
            y4 += 12;//2*10;
            x2 -= 12;//2*10;
            y2 -= 12;//2*10;
            x1 -= 10;//2*8;
            y1 += 32;//2*30;
        }

        /*canvas.drawText("N", 0, 1, x3 - 10, y3 - 10, mPaint);
        canvas.drawText("S", 0, 1, x4 - 8, y4 + 30, mPaint);
        canvas.drawText("E", 0, 1, x2 + 10, y2 + 10, mPaint);
        canvas.drawText("W", 0, 1, x1 - 30, y1 + 10, mPaint);*/

        /*canvas.drawText("N", 0, 1, x3 - 12, y3 - 12, mPaint);
        canvas.drawText("S", 0, 1, x4 - 10, y4 + 32, mPaint);
        canvas.drawText("E", 0, 1, x2 + 12, y2 + 12, mPaint);
        canvas.drawText("W", 0, 1, x1 - 32, y1 + 12, mPaint);*/


        /*canvas.drawText("N", 0, 1, x3 - 2*10, y3 - 2*10, mPaint);
        canvas.drawText("S", 0, 1, x4 - 2*8, y4 + 2*30, mPaint);
        canvas.drawText("E", 0, 1, x2 + 2*10, y2 + 2*10, mPaint);
        canvas.drawText("W", 0, 1, x1 - 2*30, y1 + 2*10, mPaint);*/

        /*canvas.drawText("N", 0, 1, x3 - 15, y3 - 15, mPaint);
        canvas.drawText("S", 0, 1, x4 - 13, y4 + 35, mPaint);
        canvas.drawText("E", 0, 1, x2 + 15, y2 + 15, mPaint);
        canvas.drawText("W", 0, 1, x1 - 35, y1 + 15, mPaint);*/
    }

    private boolean logicalPart(float cordX, float cordY) {
        if (x > cordX - epsilon && x < cordX + epsilon && y > cordY - epsilon && y < cordY + epsilon) {
            temp1 = (float) Math.atan2(cordX - cx, cordY - cy);
            temp2 = (float) Math.atan2(x - cx, y - cy);

            temp1 = (temp1 > temp2) ? temp1 - temp2 : temp2 - temp1;
            temp1 = (float) Math.toDegrees(temp1);
            Log.d("Temp1 is ", temp1 + "");

            /*if (y < cordY) {
                angle += temp1;
            } else if (y > cordY) {
                angle -= temp1;
            } else if (y > cordY ) {

            }
            else if (y < cordY && x > cordX) {
                angle -= temp1;
            } */

            /*if (y < cordY) {
                angle += temp1;
            } else if (y > cordY) {
                angle -= temp1;
            } else if (x < cordX) {
                angle -= temp1;
            } else if (x > cordX) {
                angle += temp1;
            }*/
            if (y < cordY && x >= cx) {
                angle += temp1;
            } else if (y > cordY && x >= cx) {
                angle -= temp1;
            } else if (y < cordY && x <= cx) {
                angle -= temp1;
            } else if (y > cordY && x <= cx) {
                angle += temp1;
            }

            Log.d("angle after logical part", angle + "");
            //if (angle < 0) angle += 360;
            if (angle > 360) angle -= 360;

            /*if ((y < centerY && x > oldX)
                || (y > centerY && x < oldX))
                rotation += xSpeed;
            else if ((y < centerY && x < oldX)
                     || (y > centerY && x > oldX))
                rotation -= xSpeed;

            if ((x > centerX && y > oldY)
                || (x < centerX && y < oldY))
                rotation += ySpeed;
            else if ((x > centerX && y < oldY)
                     || (x < centerX && y > oldY))
                rotation -= ySpeed;
            */

            /*if (y < cordY && temp2 >= 0) {
                angle += temp1;
            } else if (y > cordY && temp2 >= 0) {
                angle -= temp1;
            } else if (y < cordY && temp2 <= 0) {
                angle -= temp1;
            } else if (y > cordY && temp2 <= 0) {
                angle += temp1;
            }*/

            return true;
        }

        /*return false;
        if (x > cordX - epsilon && x < cordX + epsilon && y > cordY - epsilon && y < cordY + epsilon) {
            temp = (float) Math.atan2(x - cx, y - cy);
            return true;
        }*/

        return false;
    }

    private void logPoints() {
        Log.d("This is x1", x1 + "");
        Log.d("This is y1", y1 + "");
        Log.d("This is x2", x2 + "");
        Log.d("This is y2", y2 + "");
        Log.d("This is x3", x3 + "");
        Log.d("This is y3", y3 + "");
        Log.d("This is x4", x4 + "");
        Log.d("This is y4", y4 + "");
    }


    private boolean determinantAction() {
        getPeaksOfRhumb();

        if(logicalPart(x1, y1)) { return true; }
        if(logicalPart(x2, y2)) { return true; }
        if(logicalPart(x3, y3)) { return true; }
        if(logicalPart(x4, y4)) { return true; }

        /*if(logicalPart(x1, y1)) {
            if (temp >= 0) {
                temp = temp - 90;
                x1 = (float) (cx + radius * Math.cos((double) temp));
                y1 = (float) (cy - radius * Math.sin((double) temp));
                x2 = 2 * cx - x1;
                y2 = 2 * cy - y1;
                x3 = (float) (cx + radius * Math.cos((double) temp + Math.PI / 2));
                y3 = (float) (cy - radius * Math.sin((double) temp + Math.PI / 2));
                x4 = 2 * cx - x3;
                y4 = 2 * cy - y3;
            } else {
                temp = temp + 90;
                x1 = (float) (cx - radius * Math.cos((double) temp));
                y1 = (float) (cy + radius * Math.sin((double) temp));
                x2 = 2 * cx - x1;
                y2 = 2 * cy - y1;
                x3 = (float) (cx - radius * Math.cos((double) temp + Math.PI / 2));
                y3 = (float) (cy + radius * Math.sin((double) temp + Math.PI / 2));
                x4 = 2 * cx - x3;
                y4 = 2 * cy - y3;
            }
            return true;
        }
        else
        if(logicalPart(x2, y2)) {
            if (temp >= 0) {
                temp = temp - 90;
                x2 = (float) (cx + radius * Math.cos((double) temp));
                y2 = (float) (cy - radius * Math.sin((double) temp));
                x1 = 2 * cx - x2;
                y1 = 2 * cy - y2;
                x4 = (float) (cx + radius * Math.cos((double) temp + Math.PI / 2));
                y4 = (float) (cy - radius * Math.sin((double) temp + Math.PI / 2));
                x3 = 2 * cx - x4;
                y3 = 2 * cy - y4;
            } else {
                temp = temp + 90;
                x2 = (float) (cx - radius * Math.cos((double) temp));
                y2 = (float) (cy + radius * Math.sin((double) temp));
                x1 = 2 * cx - x2;
                y1 = 2 * cy - y2;
                x4 = (float) (cx - radius * Math.cos((double) temp + Math.PI / 2));
                y4 = (float) (cy + radius * Math.sin((double) temp + Math.PI / 2));
                x3 = 2 * cx - x4;
                y3 = 2 * cy - y4;
            }
            return true;
        }
        else
        if(logicalPart(x3, y3)) {
            if (temp >= 0) {
                temp = temp - 90;
                x3 = (float) (cx + radius * Math.cos((double) temp));
                y3 = (float) (cy - radius * Math.sin((double) temp));
                x4 = 2 * cx - x3;
                y4 = 2 * cy - y3;
                x2 = (float) (cx + radius * Math.cos((double) temp + Math.PI / 2));
                y2 = (float) (cy - radius * Math.sin((double) temp + Math.PI / 2));
                x1 = 2 * cx - x2;
                y1 = 2 * cy - y2;
            } else {
                temp = temp + 90;
                x3 = (float) (cx - radius * Math.cos((double) temp));
                y3 = (float) (cy + radius * Math.sin((double) temp));
                x4 = 2 * cx - x3;
                y4 = 2 * cy - y3;
                x2 = (float) (cx - radius * Math.cos((double) temp + Math.PI / 2));
                y2 = (float) (cy + radius * Math.sin((double) temp + Math.PI / 2));
                x1 = 2 * cx - x2;
                y1 = 2 * cy - y2;
            }
            return true;
        }
        else
        if(logicalPart(x4, y4)) {
            if (temp >= 0) {
                temp = temp - 90;
                x4 = (float) (cx + radius * Math.cos((double) temp));
                y4 = (float) (cy - radius * Math.sin((double) temp));
                x3 = 2 * cx - x4;
                y3 = 2 * cy - y4;
                x1 = (float) (cx + radius * Math.cos((double) temp + Math.PI / 2));
                y1 = (float) (cy - radius * Math.sin((double) temp + Math.PI / 2));
                x2 = 2 * cx - x1;
                y2 = 2 * cy - y1;
            } else {
                temp = temp + 90;
                x4 = (float) (cx - radius * Math.cos((double) temp));
                y4 = (float) (cy + radius * Math.sin((double) temp));
                x3 = 2 * cx - x4;
                y3 = 2 * cy - y4;
                x1 = (float) (cx - radius * Math.cos((double) temp + Math.PI / 2));
                y1 = (float) (cy + radius * Math.sin((double) temp + Math.PI / 2));
                x2 = 2 * cx - x1;
                y2 = 2 * cy - y1;
            }
            return true;
        }*/
        //
        /*if(logicalPart(x1, y1)) {
            if (temp >= 0) {
                temp = temp - 90;
                x1 = (float) (cx + radius * Math.cos((double) temp / 180 * Math.PI));
                y1 = (float) (cy - radius * Math.sin((double) temp / 180 * Math.PI));
                x2 = 2 * cx - x1;
                y2 = 2 * cy - y1;
                x3 = (float) (cx + radius * Math.cos((double) temp / 180 * Math.PI + Math.PI / 2 ));
                y3 = (float) (cy - radius * Math.sin((double) temp / 180 * Math.PI + Math.PI / 2));
                x4 = 2 * cx - x3;
                y4 = 2 * cy - y3;
            } else {
                temp = temp + 90;
                x1 = (float) (cx - radius * Math.cos((double) temp / 180 * Math.PI));
                y1 = (float) (cy + radius * Math.sin((double) temp / 180 * Math.PI));
                x2 = 2 * cx - x1;
                y2 = 2 * cy - y1;
                x3 = (float) (cx - radius * Math.cos((double) temp / 180 * Math.PI + Math.PI / 2));
                y3 = (float) (cy + radius * Math.sin((double) temp / 180 * Math.PI + Math.PI / 2));
                x4 = 2 * cx - x3;
                y4 = 2 * cy - y3;
            }
            return true;
        }
        else
        if(logicalPart(x2, y2)) {
            if (temp >= 0) {
                temp = temp - 90;
                x2 = (float) (cx + radius * Math.cos((double) temp / 180 * Math.PI));
                y2 = (float) (cy - radius * Math.sin((double) temp / 180 * Math.PI));
                x1 = 2 * cx - x2;
                y1 = 2 * cy - y2;
                x4 = (float) (cx + radius * Math.cos((double) temp / 180 * Math.PI + Math.PI / 2));
                y4 = (float) (cy - radius * Math.sin((double) temp / 180 * Math.PI + Math.PI / 2));
                x3 = 2 * cx - x4;
                y3 = 2 * cy - y4;
            } else {
                temp = temp + 90;
                x2 = (float) (cx - radius * Math.cos((double) temp / 180 * Math.PI));
                y2 = (float) (cy + radius * Math.sin((double) temp / 180 * Math.PI));
                x1 = 2 * cx - x2;
                y1 = 2 * cy - y2;
                x4 = (float) (cx - radius * Math.cos((double) temp / 180 * Math.PI + Math.PI / 2));
                y4 = (float) (cy + radius * Math.sin((double) temp / 180 * Math.PI + Math.PI / 2));
                x3 = 2 * cx - x4;
                y3 = 2 * cy - y4;
            }
            return true;
        }
        else
        if(logicalPart(x3, y3)) {
            if (temp >= 0) {
                temp = temp - 90;
                x3 = (float) (cx + radius * Math.cos((double) temp / 180 * Math.PI));
                y3 = (float) (cy - radius * Math.sin((double) temp / 180 * Math.PI));
                x4 = 2 * cx - x3;
                y4 = 2 * cy - y3;
                x2 = (float) (cx + radius * Math.cos((double) temp / 180 * Math.PI + Math.PI / 2));
                y2 = (float) (cy - radius * Math.sin((double) temp / 180 * Math.PI + Math.PI / 2));
                x1 = 2 * cx - x2;
                y1 = 2 * cy - y2;
            } else {
                temp = temp + 90;
                x3 = (float) (cx - radius * Math.cos((double) temp / 180 * Math.PI));
                y3 = (float) (cy + radius * Math.sin((double) temp / 180 * Math.PI));
                x4 = 2 * cx - x3;
                y4 = 2 * cy - y3;
                x2 = (float) (cx - radius * Math.cos((double) temp / 180 * Math.PI + Math.PI / 2));
                y2 = (float) (cy + radius * Math.sin((double) temp / 180 * Math.PI + Math.PI / 2));
                x1 = 2 * cx - x2;
                y1 = 2 * cy - y2;
            }
            return true;
        }
        else
        if(logicalPart(x4, y4)) {
            if (temp >= 0) {
                temp = temp - 90;
                x4 = (float) (cx + radius * Math.cos((double) temp / 180 * Math.PI));
                y4 = (float) (cy - radius * Math.sin((double) temp / 180 * Math.PI));
                x3 = 2 * cx - x4;
                y3 = 2 * cy - y4;
                x1 = (float) (cx + radius * Math.cos((double) temp / 180 * Math.PI + Math.PI / 2));
                y1 = (float) (cy - radius * Math.sin((double) temp / 180 * Math.PI + Math.PI / 2));
                x2 = 2 * cx - x1;
                y2 = 2 * cy - y1;
            } else {
                temp = temp + 90;
                x4 = (float) (cx - radius * Math.cos((double) temp / 180 * Math.PI));
                y4 = (float) (cy + radius * Math.sin((double) temp / 180 * Math.PI));
                x3 = 2 * cx - x4;
                y3 = 2 * cy - y4;
                x1 = (float) (cx - radius * Math.cos((double) temp / 180 * Math.PI + Math.PI / 2));
                y1 = (float) (cy + radius * Math.sin((double) temp / 180 * Math.PI + Math.PI / 2));
                x2 = 2 * cx - x1;
                y2 = 2 * cy - y1;
            }
            return true;
        }
*/
        determinantPoints();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mPath.isEmpty()) {
            mPath.reset();
        }

        cx = canvas.getWidth() / 2;
        cy = canvas.getHeight() / 2;

        Log.d("canvase width", cx + "");
        Log.d("canvase height", cy + "");

        radius = (float) (Math.max(cx, cy) * 0.5);

        //if (testOfLastAngle) {
            getPeaksOfRhumb();
        //}

        logPoints();

        mPath.moveTo(x1, y1);
        mPath.lineTo(x3, y3);
        mPath.moveTo(x1, y1);
        mPath.lineTo(x4, y4);
        mPath.moveTo(x2, y2);
        mPath.lineTo(x3, y3);
        mPath.moveTo(x2, y2);
        mPath.lineTo(x4, y4);
        mPath.close();
        canvas.drawPath(mPath, mPaint1);
        //canvas.rotate(45);

        determinantPoints();

        canvas.drawText("N", 0, 1, x3, y3, mPaint2);
        canvas.drawText("S", 0, 1, x4, y4, mPaint2);
        canvas.drawText("E", 0, 1, x2, y2, mPaint2);
        canvas.drawText("W", 0, 1, x1, y1, mPaint2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /* try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch method
            e.printStackTrace();
        }*/

        x = event.getX();
        y = event.getY();

        Log.d("Current Touched X ", x + "");
        Log.d("Current Touched Y ", y + "");


        if (testOfLastAngle) {
            lastAngle = angle;
            testOfLastAngle = false;
        }

        if (determinantAction()) {
            invalidate();
        }

        /*if (event.getAction() == MotionEvent.ACTION_CANCEL) { // when stoped touch event is gnarate the ACTION_UP event

            angle = lastAzimuth;
            //lastAngle = angle;
            invalidate();
        }*/

        return true;
    }
    
    public void updateData(float angle) {
        this.angle = angle;
        invalidate();
    }
}

