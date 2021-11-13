package schoolFile;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class EightNumber {
	static Stack<int[]> v = new Stack<int[]>();
	static Stack<int[]> L1 = new Stack<int[]>();
	static Stack<int[]> L = new Stack<int[]>();
	static Stack<int[]> memoryL = new Stack<int[]>();
	static Stack<int[]> LRemove = new Stack<int[]>();
	static Stack<int[]> matchPrevious = new Stack<int[]>();
	static int col = 3;
	static int[] Goal = { 1, 2, 3, 8, 0, 4, 7, 6, 5 };
	static int countDataV, sizeV, countDataL, sizeL, countDataL1, sizeL1;

	public static void Up(int i, int[] martix) {
		if (i - 3 >= 0) {
			int transfer = martix[i - 3];
			martix[i - 3] = martix[i];
			martix[i] = transfer;
			v.push(martix);
		}
	}

	public static void Down(int i, int[] martix) {
		if (i + 3 < 9) {
			int transfer = martix[i + 3];
			martix[i + 3] = martix[i];
			martix[i] = transfer;
			v.push(martix);
		}
	}

	public static void Left(int i, int[] martix) {
		if (i % 3 > 0) {
			int transfer = martix[i - 1];
			martix[i - 1] = martix[i];
			martix[i] = transfer;
			v.push(martix);
		}
	}

	public static void Right(int i, int[] martix) {
		if (i % 3 < 2) {
			int transfer = martix[i + 1];
			martix[i + 1] = martix[i];
			martix[i] = transfer;
			v.push(martix);
		}
	}

	public static int[] StepProcess(int[] matrix, Stack<int[]> L) {
		int locationCurrent = 0;
		for (int i = 0; i < 9; i++) {
			if (matrix[i] == 0) {
				locationCurrent = i;
			}
		}
		v.clear();
		int[] matrixUP = matrix.clone();
		int[] matrixRight = matrix.clone();
		int[] matrixLeft = matrix.clone();
		int[] matrixDown = matrix.clone();
		Up(locationCurrent, matrixUP);
		Right(locationCurrent, matrixRight);
		Left(locationCurrent, matrixLeft);
		Down(locationCurrent, matrixDown);
		Stack<int[]> input = (Stack<int[]>) v.clone();
		L1 = sortstack(input);
		for (int i = 0; i < L1.size(); i++) {
			L.push(L1.get(i));
		}
		return matrix;
	}

	public static boolean GoalTest(int[] currentData) {
		boolean isGoal = false;
		int result = checkInt(currentData);
		if (result == 0) {
			return true;
		}
		return isGoal;
	}

	public static int[] dequeue(Stack<int[]> L) {
		if (LRemove.isEmpty()) {
			while (!L.isEmpty()) {
				LRemove.push(L.pop());
			}
		}
		int[] datapopup = LRemove.pop();
		LRemove.clear();
		return datapopup;
	}

	public static Stack<int[]> sortstack(Stack<int[]> input) {
		Stack<int[]> tmpStack = new Stack<int[]>();
		while (!input.isEmpty()) {
			ArrayList<int[]> datasort = new ArrayList<int[]>();
			while (input.size() != 0) {
				datasort.add(input.pop());
			}
			Collections.sort(datasort, new Comparator<int[]>() {
				@Override
				public int compare(int[] lhs, int[] rhs) {
					// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
					int n1 = checkInt(lhs);
					int n2 = checkInt(rhs);
					if (n1 > n2) {
						return -1;
					}
					if (n2 > n1) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			for (int i = 0; i < datasort.size(); i++) {
				tmpStack.add(datasort.get(i));
			}
		}
		return tmpStack;
	}

	public static int checkInt(int[] data) {
		int result = 0;
		for (int i = 0; i < 9; i++) {
			if (data[i] == Goal[i]) {
				result++;
			}
		}
		return result;
	}

	public static void main(String[] arg) {
		int[] currentMatrix = { 1, 2, 8, 5, 7, 0, 4, 6, 3 };
		System.out.println("   u   |                v                    |               L1                    |  L");
		String line1 = "", line2 = "", line3 = "";
		line1 = "       |                                     |                                     | ";
		line2 = "       |                                     |                                     | ";
		line3 = "       |                                     |                                     | ";
		int m = 0;
		while (m < 9) {
			if (m < 3) {
				line1 += currentMatrix[m] + " ";
			}
			if (m >= 3 && m < 6) {
				line2 += currentMatrix[m] + " ";
			}
			if (m >= 6 && m < 9) {
				line3 += currentMatrix[m] + " ";
			}
			m++;
		}
		System.out.println(line1);
		System.out.println(line2);
		System.out.println(line3);
		while (!GoalTest(currentMatrix)) {
			StepProcess(currentMatrix, L);
			L.addAll(memoryL);
			printMatrix3Line(currentMatrix, L);
			currentMatrix = EightNumber.dequeue(L);
		}
		int[] lastMatrix = L.pop();
		System.out.println("");
		String lastline1 = "", lastline2 = "", lastline3 = "";
		int lastm = 0;
		while (lastm < 9) {
			if (lastm < 3) {
				line1 += lastMatrix[lastm] + " ";
			}
			if (m >= 3 && m < 6) {
				line2 += lastMatrix[lastm] + " ";
			}
			if (m >= 6 && m < 9) {
				line3 += lastMatrix[lastm] + " ";
			}
			lastm++;
		}
		System.out.println(lastline1);
		System.out.println(lastline2);
		System.out.println(lastline3);
	}

	public static void printMatrix3Line(int[] currentmatrix, Stack<int[]> L) {
		String line1 = "", line2 = "", line3 = "";
		line1 = line2 = line3 = "";
		for (int i = 0; i < 9; i++) {
			if (i < 3) {
				line1 += currentmatrix[i] + " ";
			}
			if (i >= 3 && i < 6) {
				line2 += currentmatrix[i] + " ";
			}
			if (i >= 6 && i < 9) {
				line3 += currentmatrix[i] + " ";
			}
		}
		System.out.println("");
		line1 += " | ";
		line2 += " | ";
		line3 += " | ";
		int j = 0;
		countDataV = 0;
		sizeV = v.size();
		countDataL = 0;
		sizeL = L.size();
		countDataL1 = 0;
		sizeL1 = L1.size();
		while (countDataV < sizeV) {
			if (j == 5) {
				break;
			}
			int[] matrixV = v.get(countDataV);
			for (int i = 0; i < 9; i++) {
				if (i < 3) {
					line1 += matrixV[i] + " ";
				}
				if (i >= 3 && i < 6) {
					line2 += matrixV[i] + " ";
				}
				if (i >= 6 && i < 9) {
					line3 += matrixV[i] + " ";
				}
			}
			line1 += " ";
			line2 += " ";
			line3 += " ";
			countDataV++;
			j++;
		}
		while (j < 5) {
			line1 += "       ";
			line2 += "       ";
			line3 += "       ";
			j++;
		}
		line1 += " | ";
		line2 += " | ";
		line3 += " | ";
		j = 0;
		while (countDataL1 < sizeL1) {
			if (j == 5) {
				break;
			}
			int[] matrixV = L1.get(countDataL1);
			for (int i = 0; i < 9; i++) {
				if (i < 3) {
					line1 += matrixV[i] + " ";
				}
				if (i >= 3 && i < 6) {
					line2 += matrixV[i] + " ";
				}
				if (i >= 6 && i < 9) {
					line3 += matrixV[i] + " ";
				}
			}
			line1 += " ";
			line2 += " ";
			line3 += " ";
			countDataL1++;
			j++;
		}
		while (j < 5) {
			line1 += "       ";
			line2 += "       ";
			line3 += "       ";
			j++;
		}
		line1 += " | ";
		line2 += " | ";
		line3 += " | ";
		j = 0;
		while (countDataL < sizeL) {
			if (j == 5) {
				break;
			}
			int[] matrixV = L.get(countDataL);
			for (int i = 0; i < 9; i++) {
				if (i < 3) {
					line1 += matrixV[i] + " ";
				}
				if (i >= 3 && i < 6) {
					line2 += matrixV[i] + " ";
				}
				if (i >= 6 && i < 9) {
					line3 += matrixV[i] + " ";
				}
			}
			line1 += " ";
			line2 += " ";
			line3 += " ";
			countDataL++;
			j++;
		}
		while (j < 5) {
			line1 += "       ";
			line2 += "       ";
			line3 += "       ";
			j++;
		}
		j = 0;
		System.out.println(line1);
		System.out.println(line2);
		System.out.println(line3);
		while (countDataV < sizeV || countDataL < sizeL || countDataL1 < sizeL1) {
			line1 = line2 = line3 = "";
			line1 += "       | ";
			line2 += "       | ";
			line3 += "       | ";
			j = 0;
			while (countDataV < sizeV) {
				if (j == 5) {
					break;
				}
				int[] matrixV = v.get(countDataV);
				for (int i = 0; i < 9; i++) {
					if (i < 3) {
						line1 += matrixV[i] + " ";
					}
					if (i >= 3 && i < 6) {
						line2 += matrixV[i] + " ";
					}
					if (i >= 6 && i < 9) {
						line3 += matrixV[i] + " ";
					}
				}
				line1 += " ";
				line2 += " ";
				line3 += " ";
				countDataV++;
				j++;
			}
			while (j < 5) {
				line1 += "       ";
				line2 += "       ";
				line3 += "       ";
				j++;
			}
			line1 += " | ";
			line2 += " | ";
			line3 += " | ";
			j = 0;
			while (countDataL1 < sizeL1) {
				if (j == 5) {
					break;
				}
				int[] matrixV = L.get(countDataL1);
				for (int i = 0; i < 9; i++) {
					if (i < 3) {
						line1 += matrixV[i] + " ";
					}
					if (i >= 3 && i < 6) {
						line2 += matrixV[i] + " ";
					}
					if (i >= 6 && i < 9) {
						line3 += matrixV[i] + " ";
					}
				}
				line1 += " ";
				line2 += " ";
				line3 += " ";
				countDataL1++;
				j++;
			}
			while (j < 5) {
				line1 += "       ";
				line2 += "       ";
				line3 += "       ";
				j++;
			}
			line1 += " | ";
			line2 += " | ";
			line3 += " | ";
			j = 0;
			while (countDataL < sizeL) {
				if (j == 5) {
					break;
				}
				int[] matrixV = L.get(countDataL);
				for (int i = 0; i < 9; i++) {
					if (i < 3) {
						line1 += matrixV[i] + " ";
					}
					if (i >= 3 && i < 6) {
						line2 += matrixV[i] + " ";
					}
					if (i >= 6 && i < 9) {
						line3 += matrixV[i] + " ";
					}
				}
				line1 += " ";
				line2 += " ";
				line3 += " ";
				countDataL++;
				j++;
			}
			while (j < 5) {
				line1 += "       ";
				line2 += "       ";
				line3 += "       ";
				j++;
			}
			j = 0;
			System.out.println(line1);
			System.out.println(line2);
			System.out.println(line3);
		}
		memoryL = (Stack<int[]>) L.clone();
		memoryL.remove(0);
		L1.clear();
		v.clear();
	}

}
