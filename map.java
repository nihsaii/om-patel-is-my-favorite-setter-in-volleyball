float voxelSize = 20;
float mapSize = 600;
float baseAltitude = -0.3;
float noiseScale = 150;
float noiseOffset = 1000;

PImage bob;
void setup() {
    size(640, 400, P3D);

	bob = loadImage("Bob-Ross.png");
	camera(-mapSize / 2 * 1.5, -mapSize / 2 * 1.5, mapSize / 2 * 1.5);
    noStroke();
	noiseSeed(31);
	noLoop();
}

void draw() {
	background("LightSteelBlue");
	ambientLight(150, 150, 150);
	directionalLight(255, 255, 255, -0.5, 1, -0.5);
	
	pushMatrix();
	translate(150, -300, 150);
	emissiveMaterial(240, 240, 0);
	sphere(50);
	popMatrix();
	
	pushMatrix();
	translate(0, -200, -100);
	fill(255, 255, 255, 200);
	for (float i = 0; i < 20; i++) {
		sphere(20);
		translate(random(-30, 30), 0, random(-30, 30));
	}
	popMatrix();
	pushMatrix();
	translate(0, -220, -100);
	fill(255, 255, 255, 200);
	for (float i = 0; i < 10; i++) {
		sphere(20);
		translate(random(-10, 10), 0, random(-10, 10));
	}
	popMatrix();
	
  for (float x = -mapSize / 2;
			 x < mapSize / 2;
			 x += voxelSize) {
    for (float z = -mapSize / 2;
				 z < mapSize / 2;
				 z += voxelSize) {
      float value = Math.max(

					noise(
						(1 / noiseScale * x + noiseOffset),
						(1 / noiseScale * z + noiseOffset)) +
				  baseAltitude,
				  0
			);

            fill(getColor(value));

			pushMatrix();
			translate(x, 0, z);
			float maxHeight = Math.floor(value * 10);
			for (float h = 0; h <= maxHeight; h++) {
				translate(0, -voxelSize, 0);
				box(voxelSize);
			}
			

			if (value > 0.1 && value < 0.2 && random() > 0.9) {
				fill("SaddleBrown");
				translate(0, -voxelSize, 0);
				cylinder(voxelSize * 0.2, voxelSize);
				translate(0, -voxelSize, 0);
				fill("DarkGreen");
				rotateZ(PI);
				cone(voxelSize * 0.4, voxelSize * 2);
			}
			
			popMatrix();
    }
  }
}

String getColor(float value) {
	for (float ix = 0; ix < gradientStops.length; ix++) {
		if (value <= gradientStops[ix]) {
			return gradientColors[ix];
		}
	}
	
	return "White";
}

String[] gradientColors = {
	"LightSeaGreen",
	"LightGreen",
	"LimeGreen",
	"ForestGreen",
	"Green",
	"Peru",
	"Sienna",
	"Gray",
	"Gainsboro"
};
float[] gradientStops = {
	0,
	0.02,
	0.04,
	0.1,
	0.2,
	0.4,
	0.6,
	0.8,
	1
};



void mouseClicked() {
    float aspect = 840 / 908;
	float h = height * 0.9;
	float w = aspect * h;
	camera();
	ortho();
	translate(0, 0, mapSize / 2);
	image(bob, width / 2 - w * 0.9, height / 2 - h, w, h);
}
