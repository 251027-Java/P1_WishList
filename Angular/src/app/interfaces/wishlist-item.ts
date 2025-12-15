import { Brand } from "./brand";

export interface WishlistItem {
    id: number;
    name: string;
    brand: Brand;
    price: number;
}
